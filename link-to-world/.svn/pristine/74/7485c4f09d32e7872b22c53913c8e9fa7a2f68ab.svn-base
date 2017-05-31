package com.caej.product.api;

import java.util.List;

import com.caej.product.api.product.AuditingProductAuditRequest;
import com.caej.product.api.product.AuditingProductQuery;
import com.caej.product.api.product.AuditingProductResponse;
import com.caej.product.api.product.ProductAdminRequest;
import com.caej.product.api.product.ProductAdminResponse;
import com.caej.product.api.product.ProductRequest;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.UpdateProductStatusRequest;

import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public interface ProductWebService {
    @Path("/api/product/:id")
    @GET
    ProductResponse get(@PathParam("id") String id);

    @Path("/api/product")
    @GET
    List<ProductResponse> list();

    @Path("/api/product/name/:name")
    @GET
    ProductResponse getByName(@PathParam("name") String name);

    @Path("/api/product/name/:name/ignore-status")
    @GET
    ProductResponse getByNameIgnoreStatus(@PathParam("name") String name);

    @Path("/api/product/clause/count/:id")
    @GET
    Long countActiveInsuranceClause(@PathParam("id") String id);

    @Path("/api/product/claim/count/:id")
    @GET
    Long countActiveInsuranceClaim(@PathParam("id") String id);

    @Path("/api/product/find-admin")
    @PUT
    FindView<ProductAdminResponse> findAdmin(ProductAdminRequest request);

    @Path("/api/product/:id/status")
    @PUT
    void updateProductStatus(@PathParam("id") String id, UpdateProductStatusRequest request);

    @Path("/api/product/:id/admin")
    @GET
    ProductAdminResponse getAdmin(@PathParam("id") String id);

    @Path("/api/product/audit")
    @POST
    void createAudit(ProductRequest request);

    @Path("/api/product/audit/list")
    @PUT
    FindView<AuditingProductResponse> auditList(AuditingProductQuery query);

    @Path("/api/product/audit/:id")
    @DELETE
    void deleteAudit(@PathParam("id") String id);

    @Path("/api/product/audit/:id")
    @GET
    AuditingProductResponse getAudit(@PathParam("id") String id);

    @Path("/api/product/audit/:id")
    @PUT
    void audit(@PathParam("id") String id, AuditingProductAuditRequest request);

    @Path("/api/product/audit/:id/differ")
    @GET
    String auditDiffer(@PathParam("id") String id);
}
