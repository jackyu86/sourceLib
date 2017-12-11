package com.caej.site.user.web;

import static io.sited.user.web.controller.UserAJAXController.COOKIE_FROM_URL;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.insurance.api.InsuranceCountryWebService;
import com.google.common.collect.Maps;

import app.dealer.api.DealerWebService;
import app.dealer.api.dealer.DealerResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

/**
 * @author chi
 */
public class UserController {
    @Inject
    DealerWebService dealerWebService;
    @Inject
    InsuranceCountryWebService insuranceCountryWebService;

    @Path("/account/login/anonym")
    @GET
    public Response loginByAnonym(Request request) throws UnsupportedEncodingException, MalformedURLException {
        String url = URLDecoder.decode(request.cookie(COOKIE_FROM_URL).get(), "UTF-8");
        if (url.indexOf('?') > 0) {
            url += "&from=anonym";
        } else {
            url += "?from=anonym";
        }
        return Response.redirect(url);
    }

    @Path("/login")
    @GET
    public Response login2(Request request) {
        return Response.redirect("/account/login");
    }

    @Path("/account/login")
    @GET
    public Response login(Request request) {
        Optional<String> fromURL = request.cookie(COOKIE_FROM_URL);
        HashMap<String, Object> context = Maps.newHashMap();
        if (fromURL.isPresent() && fromURL.get().contains("checkout")) {
            context.put("isAnonymous", true);
        }
        return Response.template("/user/login.html", context);
    }

    @Path("/account/logout")
    @GET
    public Response logout(Request request) {
        request.session().invalidate();
        return Response.redirect("/").setCookie(COOKIE_FROM_URL, null);
    }

    @Path("/account/register")
    @GET
    public Response register(Request request) {
        Map<String, Object> context = Maps.newHashMap();
        String channelId = request.queryParam("channelId").orElse(null);
        String source = request.queryParam("source").orElse(null);
        context.put("states", insuranceCountryWebService.provinceByCountryCode("CHN"));

        context.put("isCustomer", true);
        context.put("showDealerRegister", true);
        if (channelId != null) {
            handleChannelId(context, channelId, source);
        }

        Response response = Response.template("/user/register.html", context);
        response.setCookie("channel-id", channelId);
        response.setCookie("source", source);
        return response;
    }

    @Path("/account/reset-password")
    @GET
    public Response resetPassword() {
        return Response.template("/user/reset-password.html", Maps.newHashMap());
    }

    @Path("/account/reset-password-success")
    @GET
    public Response resetPasswordSuccess() {
        return Response.template("/user/reset-password-success.html", Maps.newHashMap());
    }

    private void handleChannelId(Map<String, Object> context, String channelId, String source) {
        context.put("showDealerRegister", false);
        if ("dealer".equals(channelId)) {
            Optional<DealerResponse> dealerOptional = dealerWebService.get(source);
            if (dealerOptional.isPresent()) {
                context.put("parentDealerName", dealerOptional.get().name);
                context.put("parentDealerId", dealerOptional.get().id);
                context.put("isCustomer", false);
                context.put("showDealerRegister", true);
            }
        }
    }
}
