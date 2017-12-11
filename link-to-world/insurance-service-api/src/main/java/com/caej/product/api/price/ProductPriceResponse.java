package com.caej.product.api.price;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author chi
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductPriceResponse {
    @XmlElement(name = "price")
    public Double price = 0.0;
    @XmlElement(name = "sale_price")
    public Double salePrice = 0.0;
    @XmlElement(name = "discount")
    public Double discount = 0.0;
    @XmlElement(name = "shipping_fee")
    public Double shippingFee = 0.0;
    @XmlElement(name = "invoice_fee")
    public Double invoiceFee = 0.0;
    @XmlElement(name = "total")
    public Double total = 0.0;
    @XmlElementWrapper(name = "detail")
    public List<InsurancePrice> detail;

    @XmlRootElement(name = "insurance_price")
    public static class InsurancePrice {
        @XmlElement(name = "id")
        public String id;
        @XmlElement(name = "price")
        public Double price = 0.0;
    }
}
