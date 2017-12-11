package com.caej.admin.dealer;

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
    public Boolean surrenderMark;
    public Boolean bankAccountMark;
    public Integer rate;
}
