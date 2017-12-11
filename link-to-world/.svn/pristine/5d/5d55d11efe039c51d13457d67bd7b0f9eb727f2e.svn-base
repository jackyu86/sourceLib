package com.caej.product.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.caej.product.api.ProductDetailWebService;
import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.product.FormView;
import com.caej.product.api.product.ProductDetailResponse;
import com.caej.product.api.product.ProductFormFieldDisplayView;
import com.caej.product.domain.Product;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductInsuranceLiabilityService;
import com.caej.product.service.ProductLiabilityHelper;
import com.caej.product.service.ProductPriceService;
import com.caej.product.service.ProductService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.http.exception.NotFoundException;

/**
 * @author chi
 */
public class ProductDetailWebServiceImpl implements ProductDetailWebService {
    @Inject
    ProductService productService;
    @Inject
    ProductPriceService productPriceService;
    @Inject
    ProductFormService productFormService;
    @Inject
    ProductInsuranceLiabilityService insuranceLiabilityService;

    @Override
    public ProductDetailResponse get(String name) {
        ProductDetailResponse response = new ProductDetailResponse();
        Product product = productService.getByName(name).orElseThrow(() -> new NotFoundException("missing product, name={}", name));
        response.product = new ProductResponseBuilder(product).build();

        Map<String, Object> value = Maps.newHashMap();
        response.pdpForm = FormView.view(productFormService.form(product, value, product.pdp, "pdp"));


        List<ProductFormFieldDisplayView> plp = Lists.newArrayList();
        plp.addAll(product.plp);

        List<ProductLiabilityHelper.ProductLiability> liabilities = insuranceLiabilityService.helper(product).top(5);
        for (ProductLiabilityHelper.ProductLiability productInsuranceLiability : liabilities) {
            ProductFormFieldDisplayView view = new ProductFormFieldDisplayView();
            view.group = "liability";
            view.field = productInsuranceLiability.name;
            view.editable = true;
            plp.add(view);
        }
        response.plpForm = FormView.view(productFormService.form(product, value, plp, "plp"));

        ProductPriceRequest productPriceRequest = new ProductPriceRequest();
        productPriceRequest.productId = product.id.toHexString();
        productPriceRequest.formValue = Maps.newHashMap();
        response.price = productPriceService.calculate(productPriceRequest);
        return response;
    }
}
