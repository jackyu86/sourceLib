package com.caej.cart.domain;


import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "cart")
public class Cart {
    @Id
    public String id;

    @Field(name = "type")
    public CartType type;

    @Field(name = "created_time")
    public LocalDateTime createdTime;

    @Field(name = "created_by")
    public String createdBy;

    @Field(name = "updated_time")
    public LocalDateTime updatedTime;

    @Field(name = "updated_by")
    public String updatedBy;
}
