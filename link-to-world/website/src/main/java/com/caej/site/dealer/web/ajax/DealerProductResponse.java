package com.caej.site.dealer.web.ajax;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class DealerProductResponse {
    public String name;
    public String displayName;
    public Boolean checked;
    public List<ObjectId> categoryList;
}
