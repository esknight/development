package com.tgtRetail.product.api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Product API SprintBoot bootstrap class.
 */
@SpringBootApplication
public class ProductAPI {
    public static void main(String[] args) {
        SpringApplication.run(ProductAPI.class, args);
    }
    @RequestMapping(value = "/")
    public String productRoot() {
        return "Product API Root";
    }
}
