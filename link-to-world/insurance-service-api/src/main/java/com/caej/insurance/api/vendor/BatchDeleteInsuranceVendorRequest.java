package com.caej.insurance.api.vendor;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author miller
 */
public class BatchDeleteInsuranceVendorRequest {
    public List<ObjectId> ids;
}
