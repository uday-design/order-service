package com.osttra.orderservice.controller;

import com.osttra.orderservice.dto.OrderDTO;
import com.osttra.orderservice.dto.OrderMessage;
import com.osttra.orderservice.dto.OrderType;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.exception.InsufficientBalanceException;
import com.osttra.orderservice.exception.ValidationException;
import com.osttra.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;

@Slf4j
@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/buy")
    public OrderMessage buyAsset(@RequestBody OrderDTO order) throws AssetNotFoundException, ValidationException, InsufficientBalanceException, AccountNotFoundException {
        log.info("Buying asset " + order);
        return orderService.order(order, OrderType.BUY);
    }

    @PostMapping(path = "/sell")
    public OrderMessage sellAsset(@RequestBody OrderDTO order) throws AssetNotFoundException, ValidationException, InsufficientBalanceException, AccountNotFoundException {
        log.info("Selling asset " + order);
        return orderService.order(order, OrderType.SELL);
    }

    @PostMapping(path = "/switch")
    public OrderMessage switchAsset(@RequestBody OrderDTO order) throws AssetNotFoundException, ValidationException, InsufficientBalanceException, AccountNotFoundException {
        log.info("Switching asset " + order);
        return orderService.order(order, OrderType.SWITCH);
    }

}
