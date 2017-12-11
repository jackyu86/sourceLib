package com.caej.client;

import java.util.List;
import java.util.Optional;

import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.AuditingProductAuditRequest;
import com.caej.product.api.product.AuditingProductQuery;
import com.caej.product.api.product.AuditingProductResponse;
import com.caej.product.api.product.ProductAdminRequest;
import com.caej.product.api.product.ProductAdminResponse;
import com.caej.product.api.product.ProductRequest;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.UpdateProductStatusRequest;

import io.sited.cache.Cache;
import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class ProductWebServiceClient implements ProductWebService {
    private final Cache<ProductResponse> cache;
    private final ProductWebService productWebService;

    public ProductWebServiceClient(Cache<ProductResponse> cache, ProductWebService productWebService) {
        this.cache = cache;
        this.productWebService = productWebService;
    }

    @Override
    public ProductResponse get(@PathParam("id") String id) {
        final String prefix = "id$";
        Optional<ProductResponse> optional = cache.get(prefix + id);
        if (optional.isPresent()) {
            return optional.get();
        }
        ProductResponse productResponse = productWebService.get(id);
        cache.put(prefix + id, productResponse);
        return productResponse;
    }

    @Override
    public List<ProductResponse> list() {
        return productWebService.list();
    }

    @Override
    public ProductResponse getByName(@PathParam("name") String name) {
        final String prefix = "name$";
        Optional<ProductResponse> optional = cache.get(prefix + name);
        if (optional.isPresent()) {
            return optional.get();
        }
        ProductResponse productResponse = productWebService.getByName(name);
        cache.put(prefix + name, productResponse);
        return productResponse;
    }

    @Override
    public ProductResponse getByNameIgnoreStatus(@PathParam("name") String name) {
        return productWebService.getByNameIgnoreStatus(name);
    }

    @Override
    public Long countActiveInsuranceClause(@PathParam("id") String id) {
        return productWebService.countActiveInsuranceClause(id);
    }

    @Override
    public Long countActiveInsuranceClaim(@PathParam("id") String id) {
        return productWebService.countActiveInsuranceClaim(id);
    }

    @Override
    public FindView<ProductAdminResponse> findAdmin(ProductAdminRequest request) {
        return productWebService.findAdmin(request);
    }

    @Override
    public void updateProductStatus(@PathParam("id") String id, UpdateProductStatusRequest request) {
        productWebService.updateProductStatus(id, request);
    }

    @Override
    public ProductAdminResponse getAdmin(@PathParam("id") String id) {
        return productWebService.getAdmin(id);
    }

    @Override
    public void createAudit(ProductRequest request) {
        productWebService.createAudit(request);
    }

    @Override
    public FindView<AuditingProductResponse> auditList(AuditingProductQuery query) {
        return productWebService.auditList(query);
    }

    @Override
    public void deleteAudit(@PathParam("id") String id) {
        productWebService.deleteAudit(id);
    }

    @Override
    public AuditingProductResponse getAudit(@PathParam("id") String id) {
        return productWebService.getAudit(id);
    }

    @Override
    public void audit(@PathParam("id") String id, AuditingProductAuditRequest request) {
        productWebService.audit(id, request);
    }

    @Override
    public String auditDiffer(@PathParam("id") String id) {
        return productWebService.auditDiffer(id);
    }
}
