package com.caej.product.web;

import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.product.api.ProductSerialWebService;
import com.caej.product.api.serial.ProductSerialProductRequest;
import com.caej.product.api.serial.ProductSerialProductView;
import com.caej.product.api.serial.ProductSerialQuery;
import com.caej.product.api.serial.ProductSerialRequest;
import com.caej.product.api.serial.ProductSerialResponse;
import com.caej.product.domain.ProductSerial;
import com.caej.product.service.ProductSerialService;

import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.exception.BadRequestException;

/**
 * @author chi
 */
public class ProductSerialWebServiceImpl implements ProductSerialWebService {
    @Inject
    ProductSerialService productSerialService;

    @Override
    public ProductSerialResponse get(String id) {
        return response(productSerialService.get(new ObjectId(id)));
    }

    @Override
    public FindView<ProductSerialResponse> find(ProductSerialQuery query) {
        return FindView.map(productSerialService.find(query), this::response);
    }

    @Override
    public ProductSerialResponse create(ProductSerialRequest request) {
        return response(productSerialService.create(request));
    }

    @Override
    public void update(@PathParam("id") String id, ProductSerialRequest request) {
        productSerialService.update(new ObjectId(id), request);
    }

    @Override
    public void updateName(@PathParam("id") String id, ProductSerialRequest request) {
        productSerialService.updateName(new ObjectId(id), request);
    }

    @Override
    public void delete(@PathParam("id") String id) {
        ProductSerial productSerial = productSerialService.get(new ObjectId(id));
        if (productSerial.products != null && !productSerial.products.isEmpty()) {
            throw new BadRequestException("productSerialId", "存在属于此产品系列的产品");
        }
        productSerialService.delete(new ObjectId(id));
    }

    @Override
    public void addProduct(@PathParam("id") String id, ProductSerialProductRequest request) {
        productSerialService.addProduct(new ObjectId(id), request);
    }

    @Override
    public void removeProduct(@PathParam("id") String id, ProductSerialProductRequest request) {
        productSerialService.removeProduct(new ObjectId(id), request);
    }

    @Override
    public void updateProduct(@PathParam("id") String id, ProductSerialProductRequest request) {
        productSerialService.updateProduct(new ObjectId(id), request);
    }

    private ProductSerialResponse response(ProductSerial instance) {
        ProductSerialResponse response = new ProductSerialResponse();
        response.id = instance.id;
        response.name = instance.name;
        response.products = instance.products.stream().map(productSerialProduct -> {
            ProductSerialProductView productSerialProductView = new ProductSerialProductView();
            productSerialProductView.productName = productSerialProduct.productName;
            productSerialProductView.displayName = productSerialProduct.displayName;
            return productSerialProductView;
        }).collect(Collectors.toList());
        response.createdTime = instance.createdTime;
        response.createdBy = instance.createdBy;
        response.updatedTime = instance.updatedTime;
        response.updatedBy = instance.updatedBy;
        return response;
    }
}
