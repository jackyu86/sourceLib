package com.caej.site.checkout;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.client.ProductWebServiceClient;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.product.FieldGroupView;
import com.caej.product.api.product.FieldView;
import com.caej.product.api.product.FormView;
import com.caej.product.api.product.ProductResponse;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.product.DealerProductView;
import io.sited.customer.api.CustomerWebService;
import io.sited.customer.api.customer.CustomerResponse;
import io.sited.customer.api.customer.GenderView;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.template.impl.code.Objects;
import io.sited.user.web.User;
import io.sited.util.JSON;

/**
 * @author Jonathan.Guo
 */
public class CheckoutController {
    @Inject
    ProductWebServiceClient productWebServiceClient;
    @Inject
    ProductFormWebService productFormWebService;
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    InsuranceVendorWebService insuranceVendorWebService;
    @Inject
    DealerProductWebService dealerProductWebService;
    @Inject
    CustomerWebService customerWebService;

    @Path("/checkout/statement")
    @GET
    public Response product(Request request) {
        return Response.template("/checkout/statement.html", Maps.newHashMap());
    }

    @Path("/checkout")
    @GET
    public Response checkout(Request request) {
        String checkoutId = request.queryParam("id").orElseThrow(() -> new NotFoundException("missing checkoutId"));
        Optional<String> checkoutRequestOptional = request.session().get("checkout-request:" + checkoutId);
        if (!checkoutRequestOptional.isPresent()) {
            throw new NotFoundException("missing checkout request");
        }
        CheckoutSessionRequest checkoutSessionRequest = JSON.fromJSON(checkoutRequestOptional.get(), CheckoutSessionRequest.class);

        Map<String, Object> context = Maps.newHashMap();
        String id = checkoutSessionRequest.productId;
        context.put("id", id);
        context.put("checkoutId", checkoutId);

        ProductResponse product;
        if (checkoutSessionRequest.value.get("product") != null && ((Map) (checkoutSessionRequest.value.get("product"))).get("serial") != null) {
            product = productWebServiceClient.getByName(((Map) (checkoutSessionRequest.value.get("product"))).get("serial").toString());
        } else {
            product = productWebServiceClient.get(id);
        }
        context.put("product", product);

        Optional<String> from = request.queryParam("from");
        boolean isAnonym = from.isPresent() && from.get().equals("anonym");

        FormView formView = productFormWebService.checkout(id, Maps.newHashMap(checkoutSessionRequest.value));
        if (isAnonym) {
            formView = addAnonymForm(formView);
            context.put("isDealer", false);
        } else {
            Optional<DealerUserResponse> dealerUserResponse = dealerUserWebService.get(request.require(User.class).id);
            if (dealerUserResponse.isPresent()) {
                Optional<DealerProductView> optional = dealerProductWebService.getByDealerIdAndProductName(dealerUserResponse.get().dealerId, product.name);
                if (!optional.isPresent()) return Response.redirect("/checkout/forbidden");
            } else {
                User user = request.require(User.class);
                if (!Strings.isNullOrEmpty(user.fullName)) {
                    fillPolicyHolder(user, formView);
                }
            }
            context.put("isDealer", dealerUserResponse.isPresent());
        }
        context.put("form", formView);
        context.put("vendor", insuranceVendorWebService.get(product.insuranceVendorId.toHexString()));
        return Response.template("/checkout/checkout.html", context);
    }

    @Path("/checkout/forbidden")
    @GET
    public Response forbidden(Request request) {
        return Response.template("/checkout/forbidden.html", Maps.newHashMap());
    }

    private FormView addAnonymForm(FormView formView) {
        FieldGroupView fieldGroupView = new FieldGroupView();
        fieldGroupView.name = "info";
        fieldGroupView.displayName = "基本信息";
        FieldView fieldView = new FieldView();
        fieldView.name = "phoneOrEmail";
        fieldView.groupName = "info";
        fieldView.type = "phoneOrEmail";
        fieldView.displayName = "常用邮箱或联系电话";
        fieldView.options = Maps.newHashMap();
        fieldView.options.put("notNull", true);
        fieldView.options.put("notNullMessage", "不能为空");
        fieldView.options.put("phoneOrEmail", "请输入正确格式手机号或邮箱");
        fieldView.editable = true;
        fieldView.displayAs = "";
        fieldGroupView.fields = Lists.newArrayList(fieldView);
        formView.groups.add(0, fieldGroupView);

        HashMap<Object, Object> value = Maps.newHashMap();
        value.put("info", "");
        formView.value.put("info", value);
        return formView;
    }

    private void fillPolicyHolder(User user, FormView formView) {
        CustomerResponse customer = customerWebService.get(user.id);
        FieldGroupView group = formView.fieldGroup("policy-holder");
        Map<String, Object> values = Maps.newHashMap();
        for (FieldView field : group.fields) {
            if (Objects.equals("name", field.name)) {
                if (user.fullName != null) {
                    values.put("name", user.fullName);
                    field.editable = false;
                }
            } else if (Objects.equals("phone", field.name)) {
                if (user.phone != null) {
                    values.put("phone", user.phone);
                    field.editable = false;
                }
            } else if (Objects.equals("email", field.name)) {
                if (user.email != null) {
                    values.put("email", user.email);
                    field.editable = false;
                }
            } else if (Objects.equals("birthDate", field.name)) {
                if (customer.birthday != null) {
                    values.put("birthDate", customer.birthday);
                    field.editable = false;
                }
            } else if (Objects.equals("age", field.name)) {
                if (customer.birthday != null) {
                    values.put("age", LocalDate.now().getYear() - customer.birthday.getYear());
                    field.editable = false;
                }
            } else if (Objects.equals("gender", field.name)) {
                if (customer.gender != null) {
                    values.put("gender", customer.gender == GenderView.MALE ? "M" : "F");
                    field.editable = false;
                }
            } else if (Objects.equals("id", field.name)) {
                if (customer.identification != null) {
                    values.put("id", customer.identification);
                    field.editable = false;
                }
            }
        }
        formView.value.put("policy-holder", values);
    }
}
