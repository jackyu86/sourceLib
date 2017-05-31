package com.caej.product.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chi
 */
@Entity(name = "product_serial")
public class ProductSerial {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "products")
    public List<ProductSerialProduct> products;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
