package com.caej.insurance.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

/**
 * @author miller
 */
@Entity(name = "enum_yes_or_not_type")
public class EnumYesOrNotType {
    @Id
    public ObjectId id;
    @Field(name = "name")
    public String name;
    @Field(name = "value")
    public String value;
    @Field(name = "display_order")
    public Integer displayOrder;
}