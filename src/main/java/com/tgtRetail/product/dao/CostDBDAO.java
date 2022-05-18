
package com.tgtRetail.product.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoExecutionTimeoutException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.tgtRetail.product.api.Cost;
import org.bson.Document ;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;


import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class CostDBDAO {
    private Cost cost;


    /**
     * Constructor to retrieve the cost information from the MongoDB.
     * @param productId The unique identifier got a given product.
     */
    public CostDBDAO(final String productId) {
        try {
            MongoClient client = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase dbObj = client.getDatabase("product");

            MongoCollection<Document> pricingCollection = dbObj.getCollection("pricing");

            //Bson filter = eq("product_id", "13860428");
            Bson filter = eq("product_id", productId);
            Document costDoc = pricingCollection.find(filter).first() ;

            if(costDoc == null){
                cost = new Cost(productId);
            }else{

                cost = new Cost(productId);
                cost.setCurrency_code(costDoc.get("currency").toString());
                cost.setValue(Float.parseFloat(costDoc.get("price").toString()));
            }

        } catch ( MongoTimeoutException exp){
            System.out.println("Timeout Exception msg: \n"+ exp.getMessage());
        }catch (MongoExecutionTimeoutException exp){
            System.out.println("Execution timeout msg: \n"+ exp.getMessage());
        }catch (Exception exp){
            System.out.println("Execution thrown while processing cost w/ Mongo DB. msg: \n"+ exp.getMessage());
        }

    }

    /**
     * Method used to update the cost if entry exists otherwise price entry will be created.
     * @param productId The unique identifier for the product of interest.
     * @param costValue New price for the product.
     */
   public void updateCostValue(final String productId, final float costValue){

       BasicDBObject query = new BasicDBObject();
        query.put("product_id", productId);

        BasicDBObject newPriceDoc = new BasicDBObject();
        newPriceDoc.put("price", costValue + "");

        BasicDBObject updatePriceDoc = new BasicDBObject();
        updatePriceDoc.put("$set", newPriceDoc);

       //Get updated cost
       CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
               fromProviders(PojoCodecProvider.builder().automatic(Boolean.TRUE).build()));
       MongoClient client = MongoClients.create("mongodb://localhost:27017");
       MongoDatabase dbObj = client.getDatabase("product");

       MongoCollection<Cost> pricingCostCollection = dbObj.getCollection("pricing",Cost.class).withCodecRegistry(pojoCodecRegistry);

       pricingCostCollection.updateOne(query, updatePriceDoc);
       cost.setValue(costValue);
    }

    public Cost getCost() {
        return cost;
    }

    /**
     * Used for testing method.
     * @param args Command line args for testing.
     */
    public static void main(String[] args) {
        CostDBDAO costDBDAO =  new CostDBDAO("13860428");
        costDBDAO.updateCostValue("13860428", 3.88f);
    }
}
