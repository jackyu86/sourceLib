package com.caej.product.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.category.InsuranceCategoryNodeResponse;
import com.caej.insurance.api.category.InsuranceCategoryResponse;
import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.product.BatchGetProductRequest;
import com.caej.product.api.product.FormView;
import com.caej.product.api.product.ProductFormFieldDisplayView;
import com.caej.product.api.product.ProductSearchCategoryTreeResponse;
import com.caej.product.api.product.ProductSearchFirstLevelCategoryResponse;
import com.caej.product.api.product.SearchProductRequest;
import com.caej.product.api.product.SearchProductResponse;
import com.caej.product.domain.Product;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductInsuranceLiabilityService;
import com.caej.product.service.ProductLiabilityHelper;
import com.caej.product.service.ProductPriceService;
import com.caej.product.service.ProductService;
import com.caej.product.service.client.InsuranceVendorWebServiceClient;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.sited.db.FindView;
import io.sited.form.Form;

/**
 * @author chi
 */
public class ProductSearchWebServiceImpl implements ProductSearchWebService {
    private final Logger logger = LoggerFactory.getLogger(ProductSearchWebServiceImpl.class);
    @Inject
    ProductService productService;
    @Inject
    InsuranceVendorWebServiceClient insuranceVendorWebService;
    @Inject
    ProductInsuranceLiabilityService productInsuranceLiabilityService;
    @Inject
    ProductPriceService productPriceService;
    @Inject
    ProductFormService productFormService;
    @Inject
    InsuranceCategoryWebService insuranceCategoryWebService;

    @Override
    public FindView<SearchProductResponse> search(SearchProductRequest request) {
        FindView<Product> products = productService.search(request);
        return FindView.map(products, this::response);
    }

    @Override
    public FindView<String> searchV2(SearchProductRequest request) {
        FindView<Product> products = productService.search(request);
        return FindView.map(products, product -> product.name);
    }

    @Override
    public FindView<SearchProductResponse> batchGet(BatchGetProductRequest request) {
        FindView<Product> products = productService.batchGet(request.productNames);
        return FindView.map(products, this::response);
    }

    @Override
    public ProductSearchCategoryTreeResponse categoryTree() {
        List<String> categoryIds = Lists.newArrayList(productService.categorySet());
        Map<String, InsuranceCategoryNodeResponse> index = Maps.newHashMap();
        List<InsuranceCategoryNodeResponse> result = Lists.newArrayList();
        categoryIds.forEach(objectId -> {
            InsuranceCategoryNodeResponse node = new InsuranceCategoryNodeResponse();
            node.category = insuranceCategoryWebService.get(objectId);
            node.children = Lists.newArrayList();
            if (node.category.parentId == null) {
                result.add(node);
            }
            index.put(node.category.id.toHexString(), node);
        });
        categoryIds.forEach(objectId -> {
            InsuranceCategoryNodeResponse node = index.get(objectId);
            if (node.category.parentId != null) {
                InsuranceCategoryNodeResponse parent = index.get(node.category.parentId.toString());
                if (parent == null) {
                    logger.info("missing parent category, id={}, parentId={}", node.category.id, node.category.parentId);
                } else {
                    parent.children.add(node);
                }
            }
        });
        result.sort((InsuranceCategoryNodeResponse o1, InsuranceCategoryNodeResponse o2) -> o1.category.displayOrder.compareTo(o2.category.displayOrder));
        ProductSearchCategoryTreeResponse response = new ProductSearchCategoryTreeResponse();
        response.list = result;
        return response;
    }

    @Override
    public ProductSearchFirstLevelCategoryResponse firstLevelCategory() {
        List<String> categoryIds = Lists.newArrayList(productService.categorySet());
        List<InsuranceCategoryResponse> result = Lists.newArrayList();
        categoryIds.forEach(categoryId -> {
            InsuranceCategoryResponse insuranceCategoryResponse = insuranceCategoryWebService.get(categoryId);
            if (insuranceCategoryResponse.parentId == null) result.add(insuranceCategoryResponse);
        });
        result.sort((InsuranceCategoryResponse o1, InsuranceCategoryResponse o2) -> o1.displayOrder.compareTo(o2.displayOrder));
        ProductSearchFirstLevelCategoryResponse response = new ProductSearchFirstLevelCategoryResponse();
        response.list = result;
        return response;
    }

    private SearchProductResponse response(Product product) {
        SearchProductResponse response = new SearchProductResponse();
        response.id = product.id.toHexString();
        response.name = product.name;
        response.displayName = product.displayName;
        response.highlightContent = product.highlightContent;
        response.vendor = insuranceVendorWebService.get(product.insuranceVendorId.toHexString());
        response.specialFunction = product.specialFunction;
        response.specialRule = product.specialRule;

        List<ProductFormFieldDisplayView> plp = Lists.newArrayList();
        plp.addAll(product.plp);

        List<ProductLiabilityHelper.ProductLiability> liabilities = productInsuranceLiabilityService.helper(product).top(5);
        for (ProductLiabilityHelper.ProductLiability productInsuranceLiability : liabilities) {
            ProductFormFieldDisplayView view = new ProductFormFieldDisplayView();
            view.group = "liability";
            view.field = productInsuranceLiability.name;
            view.editable = true;
            plp.add(view);
        }

        Form form = productFormService.form(product, Maps.newHashMap(), plp, "plp");
        response.form = FormView.view(form);

        ProductPriceRequest productPriceRequest = new ProductPriceRequest();
        productPriceRequest.productId = product.id.toHexString();
        productPriceRequest.formValue = Maps.newHashMap();
        response.price = productPriceService.calculate(productPriceRequest);
        return response;
    }
}
