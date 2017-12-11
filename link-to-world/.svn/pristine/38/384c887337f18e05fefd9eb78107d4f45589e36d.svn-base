package com.caej.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.product.api.serial.ProductSerialProductRequest;
import com.caej.product.api.serial.ProductSerialProductView;
import com.caej.product.api.serial.ProductSerialQuery;
import com.caej.product.api.serial.ProductSerialRequest;
import com.caej.product.domain.ProductSerial;
import com.caej.product.domain.ProductSerialProduct;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.BadRequestException;

/**
 * @author chi
 */
public class ProductSerialService {
    @Inject
    MongoRepository<ProductSerial> repository;


    public ProductSerial get(ObjectId id) {
        return repository.get(id);
    }

    public FindView<ProductSerial> find(ProductSerialQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", new Document("$regex", query.name));
        }
        if (!Strings.isNullOrEmpty(query.order)) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public Optional<ProductSerial> findByProductName(String productName) {
        return repository.query(new Document("products", new Document("$elemMatch", new Document("product_name", productName)))).findOne();
    }

    public ProductSerial create(ProductSerialRequest request) {
        ProductSerial productSerial = new ProductSerial();
        productSerial.name = request.name;
        productSerial.products = request.products.stream().map(this::productSerialProduct).collect(Collectors.toList());
        productSerial.createdBy = request.requestBy;
        productSerial.updatedBy = request.requestBy;
        productSerial.createdTime = LocalDateTime.now();
        productSerial.updatedTime = LocalDateTime.now();
        repository.insert(productSerial);
        return productSerial;
    }

    public void update(ObjectId id, ProductSerialRequest request) {
        ProductSerial origin = repository.get(id);
        origin.name = request.name;
        origin.products = request.products.stream().map(this::productSerialProduct).collect(Collectors.toList());
        origin.updatedBy = request.requestBy;
        origin.updatedTime = LocalDateTime.now();
        repository.update(id, origin);
    }

    public void updateName(ObjectId id, ProductSerialRequest request) {
        ProductSerial origin = repository.get(id);
        origin.name = request.name;
        origin.updatedBy = request.requestBy;
        origin.updatedTime = LocalDateTime.now();
        repository.update(id, origin);
    }

    public void delete(ObjectId id) {
        repository.get(id);
        repository.delete(id);
    }

    private ProductSerialProduct productSerialProduct(ProductSerialProductView view) {
        ProductSerialProduct productSerialProduct = new ProductSerialProduct();
        productSerialProduct.displayName = view.displayName;
        productSerialProduct.productName = view.productName;
        return productSerialProduct;
    }

    public void addProduct(ObjectId id, ProductSerialProductRequest request) {
        ProductSerial origin = repository.get(id);
        origin.products.forEach(productSerialProduct -> {
            if (productSerialProduct.productName.equals(request.productName)) {
                throw new BadRequestException("productSerialProduct", "productSerial.error.productExist");
            }
        });
        ProductSerialProduct productSerialProduct = new ProductSerialProduct();
        productSerialProduct.productName = request.productName;
        productSerialProduct.displayName = request.serialDisplayName;
        origin.products.add(productSerialProduct);
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public void removeProduct(ObjectId id, ProductSerialProductRequest request) {
        List<ProductSerialProduct> list = Lists.newArrayList();
        ProductSerial origin = repository.get(id);
        Boolean productExist = false;
        for (ProductSerialProduct productSerialProduct : origin.products) {
            if (!productSerialProduct.productName.equals(request.productName)) {
                list.add(productSerialProduct);
            } else {
                productExist = true;
            }
        }
        if (!productExist) throw new BadRequestException("productSerialProduct", "productSerial.error.productNotExist");
        origin.products = list;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public void updateProduct(ObjectId id, ProductSerialProductRequest request) {
        ProductSerial origin = repository.get(id);
        Boolean changed = false;
        for (int i = 0; i < origin.products.size(); i++) {
            if (origin.products.get(i).productName.equals(request.productName)) {
                origin.products.get(i).displayName = request.serialDisplayName;
                changed = true;
                break;
            }
        }
        if (!changed) throw new BadRequestException("productSerialProduct", "productSerial.error.productNotExist");
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }
}
