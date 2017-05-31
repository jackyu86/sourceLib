package com.caej.admin.product;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceClaimWebService;
import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.ProductWebService;
import com.caej.product.api.product.AuditingProductAuditRequest;
import com.caej.product.api.product.AuditingProductQuery;
import com.caej.product.api.product.AuditingProductResponse;
import com.caej.product.api.product.ProductAdminRequest;
import com.caej.product.api.product.ProductAdminResponse;
import com.caej.product.api.product.ProductRequest;
import com.caej.product.api.product.ProductResponse;
import com.caej.product.api.product.ProductStatusType;
import com.caej.product.api.product.SearchProductRequest;
import com.caej.product.api.product.SearchProductResponse;
import com.caej.product.api.product.UpdateProductStatusRequest;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.sited.StandardException;
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
public class ProductAdminController {
    @Inject
    ProductWebService productWebService;
    @Inject
    ProductSearchWebService productSearchWebService;
    @Inject
    InsuranceClaimWebService insuranceClaimWebService;
    @Inject
    ProductDetailResponseClient productDetailResponseClient;

    @Path("/admin/ajax/product/find")
    @PUT
    public FindView<ProductAdminResponse> find(Request request) {
        ProductAJAXRequest productAJAXRequest = request.body(ProductAJAXRequest.class);
        ProductAdminRequest productAdminRequest = new ProductAdminRequest();
        productAdminRequest.displayName = productAJAXRequest.displayName;
        productAdminRequest.page = productAJAXRequest.page;
        productAdminRequest.limit = productAJAXRequest.limit;
        productAdminRequest.latestVersion = true;
        if (productAJAXRequest.order.contains("-")) {
            productAdminRequest.order = productAJAXRequest.order.replace("-", "");
            productAdminRequest.desc = true;
        } else {
            productAdminRequest.order = productAJAXRequest.order;
            productAdminRequest.desc = false;
        }
        return productWebService.findAdmin(productAdminRequest);
    }

    @Path("/admin/ajax/product/findByCategory")
    @GET
    public FindView<SearchProductResponse> findByCategory(Request request) {
        ObjectId categoryId = request.queryParam("categoryId", ObjectId.class).orElse(null);
        SearchProductRequest searchProductRequest = new SearchProductRequest();
        searchProductRequest.page = 1;
        searchProductRequest.limit = Integer.MAX_VALUE;

        searchProductRequest.categoryIds = Lists.newArrayList(categoryId);
        return productSearchWebService.search(searchProductRequest);
    }

    @Path("/admin/ajax/product/:id/status")
    @PUT
    public void updateProductStatus(Request request) {
        String id = request.pathParam("id");
        ProductStatusAJAXRequest productStatusAJAXRequest = request.body(ProductStatusAJAXRequest.class);
        UpdateProductStatusRequest updateProductStatusRequest = new UpdateProductStatusRequest();
        ProductResponse productResponse = new ProductResponse();
        if (ProductStatusType.INACTIVE == productStatusAJAXRequest.status) {
            productResponse = productWebService.get(id);
        }
        updateProductStatusRequest.status = productStatusAJAXRequest.status;
        productWebService.updateProductStatus(id, updateProductStatusRequest);
        if (ProductStatusType.ACTIVE == productStatusAJAXRequest.status) {
            productResponse = productWebService.get(id);
        }
        productDetailResponseClient.invalidate(productResponse.name);
    }

    @Path("/admin/ajax/product/:id")
    @GET
    public ProductAdminResponse get(Request request) {
        String id = request.pathParam("id");
        return productWebService.getAdmin(id);
    }

    @Path("/admin/ajax/product/audit")
    @POST
    public void createAudit(Request request) {
        ProductCreateAJAXRequest productCreateAJAXRequest = request.body(ProductCreateAJAXRequest.class);
        ProductRequest productRequest = productCreateAJAXRequest.product;
        AdminUser adminUser = request.require(AdminUser.class);
        productRequest.requestBy = adminUser.id;
        if (!Strings.isNullOrEmpty(productRequest.name)) {
            ProductResponse check = productWebService.getByName(productRequest.name);
            if (check.version.equals(productRequest.version + 1)) {
                throw new StandardException("当前版本已过时");
            }
        }
        productWebService.createAudit(productRequest);
    }

    @Path("/admin/ajax/product/copy")
    @PUT
    public void copyProduct(Request request) {
        ProductRequest productRequest = request.body(ProductRequest.class);
        productRequest.displayName += "[副本]";
        productRequest.name = null;
        productRequest.id = null;
        productRequest.version = 0;
        productRequest.serialId = null;
        productRequest.serialDisplayName = null;
        productRequest.status = ProductStatusType.INACTIVE;
        productWebService.createAudit(productRequest);
    }

    @Path("/admin/ajax/product/audit/list")
    @PUT
    public FindView<AuditingProductResponse> auditList(Request request) {
        AuditProductAJAXRequest auditProductAJAXRequest = request.body(AuditProductAJAXRequest.class);
        AuditingProductQuery query = new AuditingProductQuery();
        query.page = auditProductAJAXRequest.page;
        query.limit = auditProductAJAXRequest.limit;
        query.status = auditProductAJAXRequest.status;
        if (auditProductAJAXRequest.order.contains("-")) {
            query.order = auditProductAJAXRequest.order.replace("-", "");
            query.desc = true;
        } else {
            query.order = auditProductAJAXRequest.order;
            query.desc = false;
        }
        return productWebService.auditList(query);
    }

    @Path("/admin/ajax/product/audit/:id")
    @DELETE
    public void deleteAudit(Request request) {
        String id = request.pathParam("id");
        productWebService.deleteAudit(id);
    }

    @Path("/admin/ajax/product/audit/:id")
    @GET
    public AuditingProductResponse getAudit(Request request) {
        String id = request.pathParam("id");
        return productWebService.getAudit(id);
    }

    @Path("/admin/ajax/product/audit/:id/differ")
    @GET
    public AuditProductDiffer getAuditDiffer(Request request) {
        String id = request.pathParam("id");
        AuditProductDiffer differ = new AuditProductDiffer();
        differ.differ = productWebService.auditDiffer(id);
        return differ;
    }

    @Path("/admin/ajax/product/audit/:id")
    @PUT
    public void audit(Request request) {
        String id = request.pathParam("id");
        AuditingProductAuditRequest auditingProductAuditRequest = request.body(AuditingProductAuditRequest.class);
        AdminUser adminUser = request.require(AdminUser.class);
        auditingProductAuditRequest.requestBy = adminUser.id;
        productWebService.audit(id, auditingProductAuditRequest);
        AuditingProductResponse audit = productWebService.getAudit(id);
        productDetailResponseClient.invalidate(audit.product.name);
    }
}
