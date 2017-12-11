package com.caej.product.api.price;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Map;

/**
 * @author chi
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductPriceRequest {
    @XmlElement(name = "product_id")
    public String productId;
    @XmlElement(name = "form_value")
    public Map<String, Object> formValue;
}
