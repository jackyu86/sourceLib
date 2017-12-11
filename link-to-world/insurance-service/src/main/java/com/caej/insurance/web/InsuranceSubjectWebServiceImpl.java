package com.caej.insurance.web;

import java.util.Optional;
import javax.inject.Inject;
import org.bson.types.ObjectId;
import com.caej.insurance.api.InsuranceSubjectWebService;
import com.caej.insurance.api.subject.InsuranceSubjectQuery;
import com.caej.insurance.api.subject.InsuranceSubjectResponse;
import com.caej.insurance.domain.InsuranceSubject;
import com.caej.insurance.service.InsuranceSubjectService;

import io.sited.db.FindView;
/**
 * @author miller
 */
public class InsuranceSubjectWebServiceImpl implements InsuranceSubjectWebService {
    @Inject
    InsuranceSubjectService insuranceSubjectService;

    @Override
    public Optional<InsuranceSubjectResponse> get(ObjectId id) {
        InsuranceSubject insuranceTarget = insuranceSubjectService.get(id);
        return Optional.ofNullable(response(insuranceTarget));
    }

    @Override
    public FindView<InsuranceSubjectResponse> find(InsuranceSubjectQuery query) {
        return FindView.map(insuranceSubjectService.find(query), this::response);
    }

    @Override
    public void delete(ObjectId id) {
        insuranceSubjectService.delete(id);
    }

    private InsuranceSubjectResponse response(InsuranceSubject insuranceSubject) {
        InsuranceSubjectResponse response = new InsuranceSubjectResponse();
        response.id = insuranceSubject.id;
        response.name = insuranceSubject.name;
        response.description = insuranceSubject.description;
        response.createdTime = insuranceSubject.createdTime;
        response.createdBy = insuranceSubject.createdBy;
        return response;
    }
}
