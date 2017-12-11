package com.caej.site.product.web.ajax;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import com.caej.product.api.ProductPriceWebService;
import com.caej.product.api.price.ProductPriceRequest;
import com.caej.product.api.price.ProductPriceResponse;

import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

/**
 * @author chi
 */
public class ProductPriceAJAXController {
    @Inject
    ProductPriceWebService productPriceWebService;

    @Path("/ajax/product/:id/price")
    @PUT("GET")
    @SuppressWarnings("unchecked")
    public Response get(Request request) throws IOException {
        String id = request.pathParam("id");
        Map<String, Object> form = request.body(Map.class);
        ProductPriceRequest productPriceRequest = new ProductPriceRequest();
        productPriceRequest.productId = id;
        productPriceRequest.formValue = form;
        ProductPriceResponse priceResponse = productPriceWebService.calculate(productPriceRequest);
        ProductPriceAJAXResponse productPriceAJAXResponse = response(priceResponse);
        return Response.body(productPriceAJAXResponse);
    }

    private ProductPriceAJAXResponse response(ProductPriceResponse priceResponse) {
        ProductPriceAJAXResponse productPriceAJAXResponse = new ProductPriceAJAXResponse();
        productPriceAJAXResponse.discount = priceResponse.discount;
        productPriceAJAXResponse.price = priceResponse.price;
        productPriceAJAXResponse.salePrice = priceResponse.salePrice;
        productPriceAJAXResponse.total = priceResponse.total;
        productPriceAJAXResponse.invoiceFee = priceResponse.invoiceFee;
        productPriceAJAXResponse.shippingFee = priceResponse.shippingFee;
        return productPriceAJAXResponse;
    }
}
