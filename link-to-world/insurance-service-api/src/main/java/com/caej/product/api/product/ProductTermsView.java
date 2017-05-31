package com.caej.product.api.product;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chi
 */
public class ProductTermsView {
    public String name;
    public List<ObjectId> mainTerms;
    public List<ObjectId> associateTerms;
}
