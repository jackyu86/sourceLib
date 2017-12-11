package io.sited.db.impl.jdbc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

/**
 * @author chi
 */
@Entity(name = "test_entity")
public class TestEntity {
    @Id
    public Long id;

    @Field(name = "name")
    public String name;

    @Field(name = "created_time")
    public LocalDateTime createdTime;
    
    @Field(name = "period_value")
    public Integer periodValue;
    
    @Field(name = "payment_method")
    public PayMethod paymentMethod;
    
    @Field(name = "flag")
    public Boolean flag;
    
    @Field(name = "total")
    public Double total;
    
    @Field(name = "value")
    public BigDecimal value;
    
    @Field(name = "update_time")
    public LocalDate updateTime;
    
}

