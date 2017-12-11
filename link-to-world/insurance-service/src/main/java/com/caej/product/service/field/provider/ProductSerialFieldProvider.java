package com.caej.product.service.field.provider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.product.domain.Product;
import com.caej.product.domain.ProductSerial;
import com.caej.product.domain.ProductSerialProduct;
import com.caej.product.service.FieldProvider;
import com.caej.product.service.FormContext;
import com.caej.product.service.ProductSerialService;
import com.caej.product.service.ProductService;
import com.google.common.collect.Lists;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.product.DealerProductView;
import io.sited.StandardException;
import io.sited.form.Field;
import io.sited.form.FieldType;
import io.sited.form.FormConfig;

/**
 * @author chi
 */
public class ProductSerialFieldProvider implements FieldProvider {
    private final Logger logger = LoggerFactory.getLogger(ProductSerialFieldProvider.class);
    private final ProductSerialService productSerialService;
    private final FormConfig formConfig;
    private final DealerProductWebService dealerProductWebService;
    private final ProductService productService;

    public ProductSerialFieldProvider(FormConfig formConfig, ProductSerialService productSerialService, DealerProductWebService dealerProductWebService, ProductService productService) {
        this.formConfig = formConfig;
        this.productSerialService = productSerialService;
        this.dealerProductWebService = dealerProductWebService;
        this.productService = productService;
    }

    @Override
    public String name() {
        return "serial";
    }

    @Override
    public Optional<Field> get(FormContext context) {
        if (!"pdp".equals(context.name) && !"plp".equals(context.name)) return Optional.empty();
        Product product = context.product;
        Optional<ProductSerial> productSerialOptional = productSerialService.findByProductName(product.name);
        if (productSerialOptional.isPresent()) {
            ProductSerial productSerial = productSerialOptional.get();
            List<ProductSerialProduct> list = Lists.newArrayList();
            for (ProductSerialProduct productSerialProduct : productSerial.products) {
                if (productService.getByName(productSerialProduct.productName).isPresent())
                    list.add(productSerialProduct);
            }
            if (context.dealerId != null) {
                list = trimProducts(context.dealerId, list);
            }
            Field field = new Field();
            field.groupName = "product";
            field.name = "serial";
            Optional<FieldType<Object>> type = formConfig.type("Serial");
            if (!type.isPresent()) {
                throw new StandardException("missing field type, type={}", "Serial");
            }
            field.type = type.get();
            field.displayName = "产品系列";
            field.options = Collections.singletonMap("products", list);
            field.editable = false;
            return Optional.of(field);
        }
        return Optional.empty();
    }

    private List<ProductSerialProduct> trimProducts(String dealerId, List<ProductSerialProduct> products) {
        List<ProductSerialProduct> list = Lists.newArrayList();
        products.forEach(productSerialProduct -> {
            Optional<DealerProductView> optional = dealerProductWebService.getByDealerIdAndProductName(dealerId, productSerialProduct.productName);
            if (optional.isPresent()) list.add(productSerialProduct);
        });
        return list;
    }
}
