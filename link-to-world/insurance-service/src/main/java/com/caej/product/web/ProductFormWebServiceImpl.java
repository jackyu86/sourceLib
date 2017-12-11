package com.caej.product.web;

import java.util.Map;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.product.FormView;
import com.caej.product.domain.Product;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductService;

import io.sited.form.Form;
import io.sited.form.validator.BeneficiaryGroupValidator;
import io.sited.form.validator.OthersGroupValidator;
import io.sited.http.PathParam;

/**
 * @author chi
 */
public class ProductFormWebServiceImpl implements ProductFormWebService {
    @Inject
    ProductFormService productFormService;
    @Inject
    ProductService productService;

    @Override
    public FormView pdp(String productId, Map<String, Object> value) {
        Product product = productService.get(new ObjectId(productId));
        Form form = productFormService.form(product, value, product.pdp, "pdp");
        return FormView.view(form);
    }

    @Override
    public FormView dealerPdp(@PathParam("productId") String productId, @PathParam("customerId") String dealerId, Map<String, Object> value) {
        Product product = productService.get(new ObjectId(productId));
        Form form = productFormService.form(product, value, product.pdp, "pdp", dealerId);
        return FormView.view(form);
    }

    @Override
    public FormView checkout(String productId, Map<String, Object> value) {
        Product product = productService.get(new ObjectId(productId));
        Form form = productFormService.form(product, value, false, "checkout");
        return FormView.view(form);
    }

    @Override
    public FormView validate(String productId, Map<String, Object> value) {
        Product product = productService.get(new ObjectId(productId));
        Form form = productFormService.form(product, value, false, "checkout");
        form.setValidator("beneficiary", new BeneficiaryGroupValidator());
        form.setValidator("others", new OthersGroupValidator());
        form.validate();
        return FormView.view(form);
    }

    @Override
    public FormView checkoutPreview(String productId, Map<String, Object> value) {
        return FormView.view(productFormService.form(productService.get(new ObjectId(productId)), value, true, "preview"));
    }
}
