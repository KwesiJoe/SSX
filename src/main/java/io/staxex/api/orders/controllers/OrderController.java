package io.staxex.api.orders.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PostMapping("/create")
    public String createOrder(){
        return "order created";
    }

    @GetMapping("/")
    public String getOrders(){
        return "all orders";
    }

}
