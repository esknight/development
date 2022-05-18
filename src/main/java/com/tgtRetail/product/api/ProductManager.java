package com.tgtRetail.product.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgtRetail.product.dao.CostDBDAO;
import com.tgtRetail.product.dao.ProductHTTPDao;

import java.io.IOException;

/**
 * ProductMapper populates the product object with the values for the request product.
 * Additionally, it will update the cost of a valid product.
 *
 */
public class ProductManager {

    private CostDBDAO costDBDAO;
    private Product product ;


    //Used to determine if the product is validated to improve performance and reduce resource utilization.
    private Boolean isValidProduct;

    /**
     * Contractor for product will reteive the product information for the id passed to the constructor.
     * @param productId The unique identifier for the product of interest.
     */
    public ProductManager(String productId) {
        ProductHTTPDao productHTTPDao = new ProductHTTPDao(productId);
        isValidProduct = productHTTPDao.isValidProduct();
         product = productHTTPDao.getProduct();

        //Short circuit code for cost if it is an invalid product.
        if (isValidProduct) {
            System.out.println("Getting product cost.");
            costDBDAO = new CostDBDAO(productId);
            product.setCurrent_price(costDBDAO.getCost());
        } else {
            product.setCurrent_price(new Cost());
        }

    }

    /**
     * Update the product cost with the new value.
     * @param jsonStr JSON string containing value to update  the products cost.
     */
    public void updateCost(String jsonStr){
        String costValue ;
        try {
            if(isValidProduct){
                ObjectMapper objMapper = new ObjectMapper();
                JsonNode jsonNode = objMapper.readTree(jsonStr);
                costValue = jsonNode.get("newPrice").asText();

                //Check for valid number.
                try{
                    float newCostValue = Float.parseFloat(costValue);
                    costDBDAO.updateCostValue(product.getId(), newCostValue);

                }catch(NumberFormatException numExp){
                    System.out.println("Bad number format..");
                }catch (NullPointerException nullExp){
                    System.out.println("Unable to access cost value.");
                    this.product.setCurrent_price(this.costDBDAO.getCost());
                }
            }else {
                this.product.setCurrent_price(new Cost());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Product getProduct() {
        return product;
    }
}
