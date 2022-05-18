package com.tgtRetail.product.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgtRetail.product.api.Product;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class ProductHTTPDao {
    private URL url ;

    private Map<String, String> productMapping ;

    private JsonNode jsonObj = null;

    final Product product;
    Boolean isValidProduct = Boolean.TRUE;

    /**
     *  Contructor to create the DAO that will retrieve the requested product informatio from url.
     * @param productId Unique id of product being requested.
     */
    public ProductHTTPDao(String productId)  {
        //Build up URL for readability.
        final String productProtocol = "https://";
        final String productHost = "redsky-uat.perf.target.com";
        final String productRoute = "/redsky_aggregations/v1/redsky/case_study_v1?key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys&tcin=";
        final String baseProductUrl = productProtocol + productHost + productRoute ;
        final String productUrl = baseProductUrl + productId ;
        System.out.println("INFO: attempting the following product it: " +  productId);
        //Used for testing..
        final String testProductUrl = baseProductUrl + "" ;

        try {
            this.url = productId != null ? new URL(productUrl) :
                    new URL(testProductUrl) ;
        } catch (MalformedURLException e) {
            System.out.println("Run time error: " + e.getMessage());
        }
        fetchProductInformation();


        product = new Product(productId);

        if (isValidProduct){
            setProductMapping();
            product.setName(getStringValue("name"));
        }else{
            product.setName("Product is undefined. Verify the information entered is correct.");
        }

    }

    public Boolean isValidProduct() {
        return isValidProduct;
    }

    public String getStringValue(final String key){
        //Remove extra quotes on Strings with replace.
        return processMapping(productMapping.get(key)).replace("\"","");
    }

    public Product getProduct() {
        return product;
    }

    private void setProductMapping(){
        HashMap<String, String> productMapping = new HashMap<>() ;
        productMapping.put("name", "product.item.product_description.title");

        this.productMapping = productMapping;
        System.out.println(processMapping(productMapping.get("name")));
    }

    /**
     * Take the "." separated string and reference the value of the JSON object.
     * @param keys the key reference value. It uses a "." to drill down into JSON objects.
     * @return key value from jsonnode without escape charters.
     */
    private String processMapping(final String keys){
        String[] keyArray = keys.split("\\.");
        JsonNode mappingNode = this.jsonObj;
        for(String key : keyArray){
            mappingNode = mappingNode.get(key);
        }

        //Remove escape character
        return mappingNode.toString().replace("\\", "");
    }

    private void fetchProductInformation() {
        BufferedReader reader ;
        StringBuilder responseContent;

        try{
            HttpURLConnection http ;
            http = (HttpURLConnection)this.url.openConnection();
            http.setRequestProperty("Accept", "application/json");
            http.setRequestMethod("GET");
            String line  ;

            responseContent = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
            http.disconnect();

            try {
                ObjectMapper objMapper = new ObjectMapper();
                jsonObj = objMapper.readTree(responseContent.toString()).get("data");

            }catch(JsonParseException msg){
                System.out.println("Issue processing object mapper" + msg.getMessage());
            }

        } catch (IOException malformedURLException){
            // Set boolean for invalid product
            isValidProduct = Boolean.FALSE;
            System.out.println(malformedURLException.getMessage());
        }

    }

}
