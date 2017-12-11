package com.caej.site.product.web.ajax;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Jonathan.Guo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductPriceAJAXResponse {
    @XmlElement(name = "price")
    public Double price;
    @XmlElement(name = "sale_price")
    public Double salePrice;
    @XmlElement(name = "discount")
    public Double discount;
    @XmlElement(name = "total")
    public Double total;
    @XmlElement(name = "invoice_fee")
    public Double invoiceFee;
    @XmlElement(name = "shipping_fee")
    public Double shippingFee;

}
