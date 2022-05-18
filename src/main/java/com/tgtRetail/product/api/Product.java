package com.tgtRetail.product.api;


import com.tgtRetail.product.dao.ProductHTTPDao;

import java.net.MalformedURLException;

public class Product {
    private String id = "";
    private String name = " Wall Mount TV";
    private Cost current_price ;// If time permits map this name w/ the rest API.

    /**
     *
     * Pojo to hold price attributes.
     *
     */
    public Product(final String productId) {
        this.setId(productId);

    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cost getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Cost current_price) {
        this.current_price = current_price;
    }
}
