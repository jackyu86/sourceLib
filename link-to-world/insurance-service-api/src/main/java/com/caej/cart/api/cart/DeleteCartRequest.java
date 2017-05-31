package com.caej.cart.api.cart;

import org.bson.types.ObjectId;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * @author chi
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteCartRequest {
    public List<ObjectId> itemIds;

    public String requestedBy;
}
