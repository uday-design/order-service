package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.OrderDTO;
import com.osttra.orderservice.dto.OrderMessage;
import com.osttra.orderservice.dto.OrderType;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.exception.InsufficientBalanceException;
import com.osttra.orderservice.exception.ValidationException;

import javax.security.auth.login.AccountNotFoundException;

public interface OrderService {

    OrderMessage order(OrderDTO order, OrderType orderType) throws AssetNotFoundException, ValidationException, InsufficientBalanceException, AccountNotFoundException;

}
