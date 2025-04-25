package com.ms.Ecomm.controller;

import com.ms.Ecomm.dto.OrderDTO;
import com.ms.Ecomm.model.OrderRequest;
import com.ms.Ecomm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

     @Autowired
    private OrderService orderService;

     // API for placing Order
    @PostMapping("/place/{userId}")
    public OrderDTO placeOrder(@PathVariable Long userId , @RequestBody OrderRequest orderRequest)
    {
        // here we are calling a method placeOrder() and sending details userId
        // it will go to service layer and get how much quantity a user selected & what is the total price
         return orderService.placeOrder(userId , orderRequest.getProductQuantities(), orderRequest.getTotalAmount());
    }

    //API for getting all orders placed by user.
    @GetMapping("/all-orders")
    public List<OrderDTO> getAllOrders()
    {
        return orderService.getAllOrders();
    }

    // API for getting all Orders using UserId
    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrderByUser(@PathVariable Long userId)
    {
        // here we with the help of orderService object we are calling getOrderByUser() method
        // and sending userId
        // this method would be in OrderService layer  with the help of JPA OrderRepo
        // it will interact with DB and when user hit an API then with the help of @PathVariable
        // Spring Extract UserId from API and bring the details of Orders to JPA REPO
        // and then from JPA Repo it would come to Service layer and from Service layer it would
        // come to Controller layer and from Controller layer we will return that data to UI.
        return orderService.getOrderByUser(userId);
    }
}
