package com.caej.insurance.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.caej.insurance.api.vendor.CreateInsuranceVendorRequest;
import com.caej.insurance.api.vendor.InsuranceVendorQuery;
import com.caej.insurance.api.vendor.UpdateInsuranceVendorRequest;
import com.caej.insurance.domain.InsuranceVendor;
import com.caej.insurance.domain.InsuranceVendorLevel;
import com.google.common.base.Strings;

import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.NotFoundException;

/**
 * @author miller
 */
public class InsuranceVendorService {
    @Inject
    MongoRepository<InsuranceVendor> repository;

    public FindView<InsuranceVendor> find(InsuranceVendorQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", new Document("$regex", query.name));
        }
        if (query.level != null) {
            filter.put("level", InsuranceVendorLevel.valueOf(query.level.name()));
        }
        if (query.order != null) {
            return repository.query(filter).sort(query.order, query.desc).page(query.page).limit(query.limit).find();
        }
        return repository.query(filter).page(query.page).limit(query.limit).find();
    }

    public void delete(ObjectId id) {
        if (!isVendorExist(id)) {
            throw new NotFoundException("missing vendor,vendor id={}", id);
        }
        repository.delete(id);
    }

    public boolean isVendorExist(ObjectId id) {
        return repository.query("_id", id).findOne().isPresent();
    }

    public void batchDelete(List<ObjectId> ids) {
        repository.batchDelete(ids);
    }

    public void update(ObjectId id, UpdateInsuranceVendorRequest request) {
        InsuranceVendor origin = get(id);
        origin.name = request.name;
        origin.level = InsuranceVendorLevel.valueOf(request.level.name());
        origin.vendorCode = request.vendorCode;
        origin.shortName = request.shortName;
        origin.description = request.description;
        origin.imageURL = request.imageURL;
        origin.licenceImageURL = request.licenceImageURL;
        origin.updatedBy = request.requestBy;
        origin.updatedTime = LocalDateTime.now();
        repository.update(id, origin);
    }

    public InsuranceVendor get(ObjectId id) {
        return repository.get(id);
    }

    public InsuranceVendor create(CreateInsuranceVendorRequest request) {
        InsuranceVendor insuranceVendor = new InsuranceVendor();
        insuranceVendor.name = request.name;
        insuranceVendor.level = InsuranceVendorLevel.valueOf(request.level.name());
        insuranceVendor.vendorCode = request.vendorCode;
        insuranceVendor.shortName = request.shortName;
        insuranceVendor.description = request.description;
        insuranceVendor.imageURL = request.imageURL;
        insuranceVendor.licenceImageURL = request.licenceImageURL;
        insuranceVendor.createdTime = LocalDateTime.now();
        insuranceVendor.updatedTime = LocalDateTime.now();
        insuranceVendor.createdBy = request.requestBy;
        insuranceVendor.updatedBy = request.requestBy;
        repository.insert(insuranceVendor);
        return insuranceVendor;
    }
}
