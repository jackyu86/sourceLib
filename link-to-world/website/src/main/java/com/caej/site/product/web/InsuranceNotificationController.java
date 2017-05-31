package com.caej.site.product.web;

import java.util.HashMap;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceDeclarationWebService;
import com.caej.insurance.api.infomation.InsuranceInformationQuestionResponse;
import com.google.common.collect.Maps;

import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;

/**
 * @author miller
 */
public class InsuranceNotificationController {
    @Inject
    InsuranceDeclarationWebService insuranceDeclarationWebService;

    @Path("/insurance-notification/:id")
    @GET
    public Response insuranceNotification(Request request) {
        ObjectId notificationId = request.pathParam("id", ObjectId.class);
        InsuranceInformationQuestionResponse notificationResponse = insuranceDeclarationWebService.get(notificationId);
        HashMap<String, Object> context = Maps.newHashMap();
        context.put("notification", notificationResponse);
        return Response.template("/insurance-notification/detail.html", context);
    }
}
