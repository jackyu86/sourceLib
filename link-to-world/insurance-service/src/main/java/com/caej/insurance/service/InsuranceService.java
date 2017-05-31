package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.insurance.InsuranceAmountView;
import com.caej.insurance.api.insurance.InsuranceLiabilityRefView;
import com.caej.insurance.api.insurance.InsurancePriceTableView;
import com.caej.insurance.api.insurance.InsuranceQuery;
import com.caej.insurance.api.insurance.InsuranceRequest;
import com.caej.insurance.domain.Insurance;
import com.caej.insurance.domain.InsuranceAmount;
import com.caej.insurance.domain.InsuranceLiabilityRef;
import com.caej.insurance.domain.InsurancePriceTable;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;

/**
 * @author chi
 */
public class InsuranceService {
    @Inject
    MongoRepository<Insurance> repository;

    public List<Insurance> batchGet(List<ObjectId> ids) {
        return repository.batchGet(ids);
    }

    public FindView<Insurance> find(InsuranceQuery insuranceQuery) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(insuranceQuery.name)) {
            filter.put("name", new Document("$regex", insuranceQuery.name));
        }
        if (!Strings.isNullOrEmpty(insuranceQuery.order)) {
            return repository.query(filter).sort(insuranceQuery.order, insuranceQuery.desc).page(insuranceQuery.page).limit(insuranceQuery.limit).find();
        }
        return repository.query(filter).page(insuranceQuery.page).limit(insuranceQuery.limit).find();
    }

    public Insurance create(InsuranceRequest request) {
        Insurance insurance = new Insurance();
        insurance.name = request.name;
        insurance.master = request.master;
        insurance.maxAmount = request.maxAmount;
        insurance.liabilities = request.liabilities.stream().map(this::liability).collect(Collectors.toList());
        insurance.priceTable = priceTable(request.priceTable);
        insurance.priceTableURL = request.priceTableURL;
        insurance.createdTime = LocalDateTime.now();
        insurance.updatedTime = LocalDateTime.now();
        insurance.createdBy = request.requestBy;
        insurance.updatedBy = request.requestBy;
        repository.insert(insurance);
        return insurance;
    }

    private InsurancePriceTable priceTable(InsurancePriceTableView view) {
        InsurancePriceTable table = new InsurancePriceTable();
        table.engine = view.engine;
        table.fixedPrice = view.fixedPrice;
        table.base = view.base;
        table.baseFactor = view.baseFactor;
        table.xFactors = view.xFactors;
        table.yFactor = view.yFactor;
        table.table = view.table;
        return table;
    }

    public void update(ObjectId id, InsuranceRequest request) {
        Insurance origin = get(id);
        origin.name = request.name;
        origin.master = request.master;
        origin.maxAmount = request.maxAmount;
        origin.liabilities = request.liabilities.stream().map(this::liability).collect(Collectors.toList());
        origin.priceTable = priceTable(request.priceTable);
        origin.priceTableURL = request.priceTableURL;
        origin.updatedTime = LocalDateTime.now();
        origin.updatedBy = request.requestBy;
        repository.update(id, origin);
    }

    public Insurance get(ObjectId id) {
        return repository.get(id);
    }

    public void delete(ObjectId id) {
        repository.delete(id);
    }

    private InsuranceLiabilityRef liability(InsuranceLiabilityRefView view) {
        InsuranceLiabilityRef ref = new InsuranceLiabilityRef();
        ref.insuranceLiabilityId = view.insuranceLiabilityId;
        ref.amount = amount(view.amount);
        ref.description = view.description;
        return ref;
    }

    private InsuranceAmount amount(InsuranceAmountView view) {
        InsuranceAmount amount = new InsuranceAmount();
        amount.type = view.type;
        amount.inputMax = view.inputMax;
        amount.inputMin = view.inputMin;
        amount.inputIncrementUnit = view.inputIncrementUnit;
        amount.selections = view.selections;
        amount.maxUnits = view.maxUnits;
        amount.minUnits = view.minUnits;
        amount.formulaName = view.formulaName;
        amount.fixedValue = view.fixedValue;
        return amount;
    }
}
