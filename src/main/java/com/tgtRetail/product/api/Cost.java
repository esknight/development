package com.tgtRetail.product.api;

import com.tgtRetail.product.dao.CostDBDAO;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Pojo to hold cost attributes.
 */
public class Cost {
    @BsonProperty(value = "value")
    private float value = 0.00f;
    @BsonProperty(value = "currency")
    private String currency_code = "USD";
    @BsonProperty(value = "product_id")
    private String productId = "";

    public Cost() {
    }

    public Cost(final String productId) {
        setCurrency_code(productId);
    }

    public float getValue() { return value; }
/*
    public String getProductId() {
        return productId;
    }
*/
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
}
