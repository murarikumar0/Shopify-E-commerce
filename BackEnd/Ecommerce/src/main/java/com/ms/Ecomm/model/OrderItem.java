package com.ms.Ecomm.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    // @Id is using for uniquely identify items.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // One order can  have multiple items.
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    // One Order can have multiple products
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity; // product can have quantity.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
