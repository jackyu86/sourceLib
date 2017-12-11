package com.caej.site.product.web.ajax;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductPriceAJAXRequest {
    @XmlElement(name = "form")
    public Map<String, Object> form;
}
