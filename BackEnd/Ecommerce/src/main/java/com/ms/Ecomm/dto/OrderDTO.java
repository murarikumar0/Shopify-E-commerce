package com.ms.Ecomm.dto;

import java.util.Date;
import java.util.List;

// we will take order details by this separate DTO Class
// coz we don't want Recursive Problem.
// coz there are several mapping in order.
public class OrderDTO {

    private Long id;

    private double totalAmount;

    private String status;

    private Date orderDate;

    private String userName;

    private String email;

    private List<OrderItemDTO> orderItems;


    public OrderDTO(Long id, double totalAmount, String status, Date orderDate, String userName, String email, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.userName = userName;
        this.email = email;
        this.orderItems = orderItems;
    }
    public OrderDTO(Long id, double totalAmount, String status, Date orderDate, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}


// NOTE : it stands for Data Transfer Object
// it transfer Data from one class to another class.
//It’s a plain class (POJO) that is used only to carry data between processes, layers, or services in an application — without any business logic.