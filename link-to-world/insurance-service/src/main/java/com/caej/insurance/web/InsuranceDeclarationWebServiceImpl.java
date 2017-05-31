package com.caej.insurance.web;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceDeclarationWebService;
import com.caej.insurance.api.infomation.InsuranceInformationQuestionResponse;
import com.caej.insurance.domain.InsuranceDeclaration;
import com.caej.insurance.service.InsuranceDeclarationService;

import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceDeclarationWebServiceImpl implements InsuranceDeclarationWebService {
    @Inject
    InsuranceDeclarationService insuranceDeclarationService;

    @Override
    public InsuranceInformationQuestionResponse get(@PathParam("id") ObjectId id) {
        return response(insuranceDeclarationService.findById(id));
    }

    private InsuranceInformationQuestionResponse response(InsuranceDeclaration insuranceDeclaration) {
        InsuranceInformationQuestionResponse response = new InsuranceInformationQuestionResponse();
        response.id = insuranceDeclaration.id;
        response.question = insuranceDeclaration.question;
        response.name = insuranceDeclaration.name;
        response.createdTime = insuranceDeclaration.createdTime;
        response.createdBy = insuranceDeclaration.createdBy;
        return response;
    }
}
