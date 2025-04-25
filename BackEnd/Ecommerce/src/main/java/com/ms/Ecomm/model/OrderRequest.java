package com.ms.Ecomm.model;

import java.util.Map;

public class OrderRequest {

    // to avoid problem of multiple mapping we will take help of dto
    //Long : which product we are taking
    //Integer : to describe the quantity of the products.
    // key -> product id
    //value -> quantity
    private Map<Long , Integer> productQuantities;

    // calculate the totalAmount of the product.
    private double totalAmount;

    public Map<Long, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
