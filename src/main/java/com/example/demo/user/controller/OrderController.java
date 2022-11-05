package com.example.demo.user.controller;

import com.example.demo.user.domain.Order;
import com.example.demo.user.dto.OrderDto;
import com.example.demo.user.model.ListWrapper;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;

    @PostMapping("/create-order")
    public ResponseEntity createOrder(@RequestBody ListWrapper<OrderDto> orders) {

        List<Order> insertOrderData = orders.getDataList().stream()
                .map(orderData -> orderData.toEntity())
                .collect(Collectors.toList());

        userService.createOrder(insertOrderData);

        return ResponseEntity.ok().build();
    }
}
