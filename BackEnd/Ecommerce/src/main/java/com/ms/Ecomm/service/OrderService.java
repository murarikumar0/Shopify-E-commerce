package com.ms.Ecomm.service;

import com.ms.Ecomm.dto.OrderDTO;
import com.ms.Ecomm.dto.OrderItemDTO;
import com.ms.Ecomm.model.OrderItem;
import com.ms.Ecomm.model.Orders;
import com.ms.Ecomm.model.Product;
import com.ms.Ecomm.model.User;
import com.ms.Ecomm.repo.OrderRepository;
import com.ms.Ecomm.repo.ProductRepository;
import com.ms.Ecomm.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ProductRepository productRepository; // to extract product data from DB using product Id.

    @Autowired
    private OrderRepository orderRepository;

    public OrderDTO placeOrder(Long userId, Map<Long, Integer> productQuantities, double totalAmount) {

        // first we will  check if the user present or not
        // if present then we will proceed further else we will return exception.
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        // make new order
        // if any user are requesting then we want the data like id , status , orderdate , total amount
        Orders order = new Orders();
        order.setUser(user); // we are setting user then we can get the data
        order.setOrderDate(new Date());
        order.setStatus("pending"); // till now order status would be pending
        order.setTotalAmount(totalAmount);


        //here we are making order item list
        // coz one user can make multiple orders
        List<OrderItem> orderItems = new ArrayList<>();

        // now we will create orderitemDTO and put data of order item in OrderItemDTO.
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();


        for(Map.Entry<Long , Integer> entry : productQuantities.entrySet())
        {
            // first it will check if the Product found  by Product ID or not
            // if it found then we will proceed further else it will throw exception.
            // here productRepo will use the ID(which is key into the map) and find the of product from DB.
            Product product = productRepository.findById(entry.getKey())
                    .orElseThrow(()-> new RuntimeException("Product not found"));

            // if product found then we will make orderItem
            // behind the scene : how much a user would order.
            // and we will make new orderItem for each product whatever a user will order.
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // set order into OrderItem.
            orderItem.setProduct(product); // set product in order item
            orderItem.setQuantity(entry.getValue()); // we will get the quantity of orderItem from DB.

            // now Any number of  order that come we will create OrderItem & add them into OrderItem List
            orderItems.add(orderItem);

            // make orderDTO ,and it required order name , price , and quantity of order
            orderItemDTOS.add(new OrderItemDTO(product.getName(),product.getPrice(), entry.getValue()));

        }
        // here setting OrderItems in Order.
        order.setOrderItems(orderItems);

        // here we will save the Oder into DB
        Orders saveOrder = orderRepository.save(order);

        // here we will show all the below details to user on UI using OrderDTO.
        return new OrderDTO(saveOrder.getId(), saveOrder.getTotalAmount(), saveOrder.getStatus(),
                saveOrder.getOrderDate(),orderItemDTOS);


    }

    public List<OrderDTO> getAllOrders() {

        // behind the scene : with the help of OrderRepo , now OrderRepo call findAllOrdersWithUsers()
        // which would be in OrderRepo and hold the Orders details which JPA would bring from DB.
        List<Orders> orders = orderRepository.findAllOrdersWithUsers();

        // here we will convert OrderList to OrderItemDTO
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // we are converting Orders into OrderItem  , we are extracting three things(product name , price ,& quantity)
    // from Orders.
    private OrderDTO convertToDTO(Orders orders) {

        List<OrderItemDTO> OrderItems =  orders.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity())).collect(Collectors.toList());

        //user want Order Details not OrderItem details that's why we will return OrderDTO
        // so we will make new OrderDTO and return it.
        return new OrderDTO(
                orders.getId(),
                orders.getTotalAmount(),
                orders.getStatus(),
                orders.getOrderDate(),

                // if  we get user then  we will return the name else we will return Unknown
                orders.getUser() != null ? orders.getUser().getName() : "Unknown",
                // if  we get user then  we will return the email else we will return Unknown
                orders.getUser() != null ? orders.getUser().getEmail() : "Unknown",
                OrderItems
        );
    }

    public List<OrderDTO> getOrderByUser(Long userId) {

        // Optional : it uses to prevent from null pointer Exception.
        Optional<User> userOp = userRepository.findById(userId);
        // how does it prevent from null pointer Exception?
        // first we have to check is the userOp empty or not
        // if it is empty then throw Runtime exception
        if(userOp.isEmpty())
        {
            throw new RuntimeException("User not found");
        }
        // if we found user then we will get User
        User user = userOp.get();
        // here we will call findByUser() method by orderRepo & hold the order list
        List<Orders> ordersList = orderRepository.findByUser(user);
        // here we will convert the orderList to DTO & return .
        return ordersList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
