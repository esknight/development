# development - Product API

This sprint boot application is used to produce a JSON product response using a restful API .
The back in isusing a MongoDB. 

The application can be started after building source using the following command assuming MonogoDB is running local the application:
    java -jar target/ProductApi-1.0-SNAPSHOT.jar
    
To test the API use the curl run the following commands:
    Retrieve Product information:
        curl http://localhost:8080/product/13860428

    To update cost:
        curl -X PUT --header 'Content-Type: application/json' -d '{"newPrice": "3.00"}' http://localhost:8080/product/13860428
          
