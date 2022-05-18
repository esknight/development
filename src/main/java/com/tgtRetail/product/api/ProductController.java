package com.tgtRetail.product.api;

import com.tgtRetail.product.util.JSONUtil;
import org.springframework.web.bind.annotation.*;

/**
 * Product control processes web product request and return the representative product in JSON format.
 */
@RestController
public class ProductController {

    /**
     * Product Rest API to retreive the product with the request ID.
     * @param id unique identifier for the product.
     * @return JSON representation of the product object.
     */
    @GetMapping(path="/product/{id}", produces = "application/json")
    public String getProduct(@PathVariable final String id) {
        ProductManager productMgr  = new ProductManager(id);

        return JSONUtil.convertStrToJSON(productMgr.getProduct());
    }

    @PutMapping(path="/product/{id}")
    public String setProductCost(@PathVariable String id, @RequestBody String jsonPrice ){
        ProductManager productMgr  = new ProductManager(id);
        productMgr.updateCost(jsonPrice);

        return JSONUtil.convertStrToJSON(productMgr.getProduct());
    }

}
