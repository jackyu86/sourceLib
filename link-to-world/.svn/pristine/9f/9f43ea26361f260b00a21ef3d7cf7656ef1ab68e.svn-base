package com.caej.insurance.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.caej.insurance.api.InsuranceWebService;
import com.caej.insurance.api.insurance.BatchGetInsuranceRequest;
import com.caej.insurance.api.insurance.InsuranceAdminRequest;
import com.caej.insurance.api.insurance.InsuranceAmountView;
import com.caej.insurance.api.insurance.InsuranceLiabilityRefView;
import com.caej.insurance.api.insurance.InsurancePriceTableView;
import com.caej.insurance.api.insurance.InsuranceQuery;
import com.caej.insurance.api.insurance.InsuranceRequest;
import com.caej.insurance.api.insurance.InsuranceResponse;
import com.caej.insurance.domain.Insurance;
import com.caej.insurance.domain.InsuranceAmount;
import com.caej.insurance.domain.InsuranceLiabilityRef;
import com.caej.insurance.domain.InsurancePriceTable;
import com.caej.insurance.service.InsuranceService;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.util.JSON;

/**
 * @author chi
 */
public class InsuranceWebServiceImpl implements InsuranceWebService {
    @Inject
    InsuranceService insuranceService;

    @Override
    public InsuranceResponse get(String id) {
        Insurance insurance = insuranceService.get(new ObjectId(id));
        return response(insurance);
    }

    @Override
    public List<InsuranceResponse> batchGet(BatchGetInsuranceRequest request) {
        List<Insurance> insurances = insuranceService.batchGet(request.ids.stream().map(ObjectId::new).collect(Collectors.toList()));
        return insurances.stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public FindView<InsuranceResponse>
    find(InsuranceQuery insuranceQuery) {
        return FindView.map(insuranceService.find(insuranceQuery), this::response);
    }

    @Override
    public InsuranceResponse create(InsuranceAdminRequest request) {
        return response(insuranceService.create(requestConvert(request)));
    }

    @Override
    public void update(@PathParam("id") String id, InsuranceAdminRequest request) {
        insuranceService.update(new ObjectId(id), requestConvert(request));
    }

    @Override
    public void delete(@PathParam("id") String id) {
        insuranceService.delete(new ObjectId(id));
    }

    private InsuranceRequest requestConvert(InsuranceAdminRequest adminRequest) {
        InsuranceRequest insuranceRequest = new InsuranceRequest();
        insuranceRequest.name = adminRequest.name;
        insuranceRequest.master = adminRequest.master;
        insuranceRequest.maxAmount = adminRequest.maxAmount;
        insuranceRequest.liabilities = adminRequest.liabilities;
        InsurancePriceTableView priceTable = new InsurancePriceTableView();
        priceTable.engine = adminRequest.priceTable.engine;
        priceTable.fixedPrice = adminRequest.priceTable.fixedPrice;
        priceTable.xFactors = adminRequest.priceTable.xFactors;
        priceTable.yFactor = adminRequest.priceTable.yFactor;
        if (!Strings.isNullOrEmpty(adminRequest.priceTable.table)) {
            priceTable.table = JSON.fromJSON(adminRequest.priceTable.table, Map.class);
        }
        priceTable.base = adminRequest.priceTable.base;
        priceTable.baseFactor = adminRequest.priceTable.baseFactor;
        insuranceRequest.priceTable = priceTable;
        return insuranceRequest;
    }

    private InsuranceResponse response(Insurance insurance) {
        InsuranceResponse insuranceResponse = new InsuranceResponse();
        insuranceResponse.id = insurance.id;
        insuranceResponse.name = insurance.name;
        insuranceResponse.master = insurance.master;
        insuranceResponse.maxAmount = insurance.maxAmount;
        insuranceResponse.liabilities = liabilities(insurance.liabilities);
        insuranceResponse.priceTable = priceTableView(insurance.priceTable);
        insuranceResponse.priceTableURL = insurance.priceTableURL;
        insuranceResponse.createdTime = insurance.createdTime;
        insuranceResponse.createdBy = insurance.createdBy;
        insuranceResponse.updatedTime = insurance.updatedTime;
        insuranceResponse.updatedBy = insurance.updatedBy;
        return insuranceResponse;
    }

    private List<InsuranceLiabilityRefView> liabilities(List<InsuranceLiabilityRef> liabilityRefs) {
        return liabilityRefs.stream().map(this::liabilityRefView).collect(Collectors.toList());
    }

    private InsurancePriceTableView priceTableView(InsurancePriceTable insurancePriceTable) {
        InsurancePriceTableView insurancePriceTableView = new InsurancePriceTableView();
        insurancePriceTableView.engine = insurancePriceTable.engine;
        insurancePriceTableView.fixedPrice = insurancePriceTable.fixedPrice;
        insurancePriceTableView.xFactors = insurancePriceTable.xFactors;
        insurancePriceTableView.yFactor = insurancePriceTable.yFactor;
        insurancePriceTableView.table = insurancePriceTable.table;
        insurancePriceTableView.createdTime = insurancePriceTable.createdTime;
        insurancePriceTableView.createdBy = insurancePriceTable.createdBy;
        insurancePriceTableView.base = insurancePriceTable.base;
        insurancePriceTableView.baseFactor = insurancePriceTable.baseFactor;
        return insurancePriceTableView;
    }

    private InsuranceLiabilityRefView liabilityRefView(InsuranceLiabilityRef insuranceLiabilityRef) {
        InsuranceLiabilityRefView insuranceLiabilityRefView = new InsuranceLiabilityRefView();
        insuranceLiabilityRefView.description = insuranceLiabilityRef.description;
        insuranceLiabilityRefView.amount = amountView(insuranceLiabilityRef.amount);
        insuranceLiabilityRefView.insuranceLiabilityId = insuranceLiabilityRef.insuranceLiabilityId;
        return insuranceLiabilityRefView;
    }

    private InsuranceAmountView amountView(InsuranceAmount insuranceAmount) {
        InsuranceAmountView insuranceAmountView = new InsuranceAmountView();
        insuranceAmountView.type = insuranceAmount.type;
        insuranceAmountView.inputMax = insuranceAmount.inputMax;
        insuranceAmountView.inputMin = insuranceAmount.inputMin;
        insuranceAmountView.inputIncrementUnit = insuranceAmount.inputIncrementUnit;
        insuranceAmountView.selections = insuranceAmount.selections;
        insuranceAmountView.minUnits = insuranceAmount.minUnits;
        insuranceAmountView.maxUnits = insuranceAmount.maxUnits;
        insuranceAmountView.formulaName = insuranceAmount.formulaName;
        insuranceAmountView.fixedValue = insuranceAmount.fixedValue;
        return insuranceAmountView;
    }
}
