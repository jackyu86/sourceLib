package com.caej.admin.productserial;

import java.util.stream.Collectors;

import javax.inject.Inject;

import com.caej.product.api.ProductSerialWebService;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.serial.ProductSerialProductView;
import com.caej.product.api.serial.ProductSerialQuery;
import com.caej.product.api.serial.ProductSerialRequest;
import com.caej.product.api.serial.ProductSerialResponse;
import com.google.common.collect.Lists;

import io.sited.admin.AdminUser;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;

/**
 * @author miller
 */
public class ProductSerialAdminController {
    @Inject
    ProductSerialWebService productSerialWebService;
    @Inject
    ProductWebService productWebService;

    @Path("/admin/ajax/product/serial")
    @POST
    public ProductSerialResponse create(Request request) {
        ProductSerialRequest productSerialRequest = request.body(ProductSerialRequest.class);
        productSerialRequest.products = Lists.newArrayList();
        AdminUser current = request.require(AdminUser.class);
        productSerialRequest.requestBy = current.username;
        return productSerialWebService.create(productSerialRequest);
    }

    @Path("/admin/ajax/product/serial/:id")
    @GET
    public ProductSerialResponse get(Request request) {
        String id = request.pathParam("id");
        return productSerialWebService.get(id);
    }

    @Path("/admin/ajax/product/serial/find")
    @PUT
    public FindView<ProductSerialFindResponse> find(Request request) {
        ProductSerialAJAXRequest productSerialAJAXRequest = request.body(ProductSerialAJAXRequest.class);
        ProductSerialQuery productSerialQuery = new ProductSerialQuery();
        productSerialQuery.name = productSerialAJAXRequest.name;
        productSerialQuery.limit = productSerialAJAXRequest.limit;
        productSerialQuery.page = productSerialAJAXRequest.page;
        if (productSerialAJAXRequest.order.contains("-")) {
            productSerialQuery.order = productSerialAJAXRequest.order.replace("-", "");
            productSerialQuery.desc = true;
        } else {
            productSerialQuery.order = productSerialAJAXRequest.order;
            productSerialQuery.desc = false;
        }
        return FindView.map(productSerialWebService.find(productSerialQuery), this::findResponse);
    }

    private ProductSerialFindResponse findResponse(ProductSerialResponse productSerialResponse) {
        ProductSerialFindResponse findResponse = new ProductSerialFindResponse();
        findResponse.id = productSerialResponse.id;
        findResponse.name = productSerialResponse.name;
        if (productSerialResponse.products != null) {
            findResponse.products = productSerialResponse.products.stream().map(this::productFindResponse).collect(Collectors.toList());
        }
        findResponse.createdTime = productSerialResponse.createdTime;
        findResponse.createdBy = productSerialResponse.createdBy;
        findResponse.updatedTime = productSerialResponse.updatedTime;
        findResponse.updatedBy = productSerialResponse.updatedBy;
        return findResponse;
    }

    private ProductSerialProductFindResponse productFindResponse(ProductSerialProductView productSerialProductView) {
        ProductSerialProductFindResponse product = new ProductSerialProductFindResponse();
        ProductResponse byName = productWebService.getByName(productSerialProductView.productName);
        product.displayName = productSerialProductView.displayName;
        product.productName = productSerialProductView.productName;
        product.productDisplayName = byName.displayName;
        return product;
    }

    @Path("/admin/ajax/product/serial/:id")
    @PUT
    public void update(Request request) {
        String id = request.pathParam("id");
        ProductSerialRequest productSerialRequest = request.body(ProductSerialRequest.class);
        AdminUser current = request.require(AdminUser.class);
        productSerialRequest.requestBy = current.username;
        productSerialWebService.updateName(id, productSerialRequest);
    }

    @Path("/admin/ajax/product/serial/:id")
    @DELETE
    public void delete(Request request) {
        String id = request.pathParam("id");
        productSerialWebService.delete(id);
    }
}
