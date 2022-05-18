package com.tgtRetail.product.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgtRetail.product.api.Product;

import java.io.IOException;

public class JSONUtil {
    /**
     * Used to convert string objects to JSON.
     * @param covertStr String to convert.
     * @return JSON string representation of string.
     */
    public String convertStrToJSON(String covertStr) {
        String rtnStr = "Invalid string. \n";
        try {
            ObjectMapper objMapper = new ObjectMapper();
            JsonNode jsonObj = objMapper.readTree(covertStr);
            rtnStr = jsonObj.toString();
        } catch (IOException e) {
            rtnStr += e.getMessage();
            //throw new RuntimeException(e);
        }

        return rtnStr;
    }

    /**
     * Used to convert product objects to JSON.
     * @param covertObj The product object to converto to the JSOn representation.
     * @return Json string representation of the product object.
     */
    public static String convertStrToJSON(Product covertObj) {
        String rtnStr = "Invalid string. \n";
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode jsonObj = objMapper.convertValue(covertObj , JsonNode.class);
        if (jsonObj != null) {
            rtnStr = jsonObj.toString();
        }

        return rtnStr;
    }
}
