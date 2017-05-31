package com.caej.insurance.web;

import javax.inject.Inject;
import org.bson.types.ObjectId;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.vendor.BatchDeleteInsuranceVendorRequest;
import com.caej.insurance.api.vendor.CreateInsuranceVendorRequest;
import com.caej.insurance.api.vendor.InsuranceVendorLevelView;
import com.caej.insurance.api.vendor.InsuranceVendorQuery;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.insurance.api.vendor.UpdateInsuranceVendorRequest;
import com.caej.insurance.domain.InsuranceVendor;
import com.caej.insurance.service.InsuranceVendorService;

import io.sited.db.FindView;
import io.sited.http.PathParam;

/**
 * @author miller
 */
public class InsuranceVendorWebServiceImpl implements InsuranceVendorWebService {
    @Inject
    InsuranceVendorService insuranceVendorService;

    @Override
    public InsuranceVendorResponse get(String id) {
        InsuranceVendor vendor = insuranceVendorService.get(new ObjectId(id));
        return response(vendor);
    }

    @Override
    public FindView<InsuranceVendorResponse> find(InsuranceVendorQuery query) {
        return FindView.map(insuranceVendorService.find(query), this::response);
    }

    @Override
    public void delete(String id) {
        insuranceVendorService.delete(new ObjectId(id));
    }

    @Override
    public void update(@PathParam("id") String id, UpdateInsuranceVendorRequest request) {
        insuranceVendorService.update(new ObjectId(id), request);
    }

    @Override
    public InsuranceVendorResponse create(CreateInsuranceVendorRequest request) {
        return response(insuranceVendorService.create(request));
    }

    @Override
    public void batchDelete(BatchDeleteInsuranceVendorRequest request) {
        insuranceVendorService.batchDelete(request.ids);
    }

    private InsuranceVendorResponse response(InsuranceVendor insuranceVendor) {
        InsuranceVendorResponse response = new InsuranceVendorResponse();
        response.id = insuranceVendor.id;
        response.level = insuranceVendor.level == null ? null : InsuranceVendorLevelView.valueOf(insuranceVendor.level.name());
        response.vendorCode = insuranceVendor.vendorCode;
        response.name = insuranceVendor.name;
        response.shortName = insuranceVendor.shortName;
        response.description = insuranceVendor.description;
        response.imageURL = insuranceVendor.imageURL;
        response.licenceImageURL = insuranceVendor.licenceImageURL;
        response.createdBy = insuranceVendor.createdBy;
        response.createdTime = insuranceVendor.createdTime;
        response.updatedBy = insuranceVendor.updatedBy;
        response.updatedTime = insuranceVendor.updatedTime;
        return response;
    }
}
