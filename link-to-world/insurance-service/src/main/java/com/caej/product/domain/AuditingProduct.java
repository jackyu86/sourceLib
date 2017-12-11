package com.caej.product.domain;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import com.caej.product.api.product.AuditingStatus;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author chi
 */
@Entity(name = "auditing_product")
public class AuditingProduct {
    @Id
    public ObjectId id;
    @Field(name = "product")
    public Product product;
    @Field(name = "product_display_name")
    public String productDisplayName;
    @Field(name = "version")
    public Integer version;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "audited_by")
    public String auditedBy;
    @Field(name = "audited_time")
    public LocalDateTime auditedTime;
    @Field(name = "comment")
    public String comment;
    @Field(name = "status")
    public AuditingStatus status;
}
