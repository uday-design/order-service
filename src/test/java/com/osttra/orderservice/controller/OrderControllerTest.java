package com.osttra.orderservice.controller;

import com.osttra.orderservice.dto.OrderDTO;
import com.osttra.orderservice.dto.OrderMessage;
import com.osttra.orderservice.dto.OrderType;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.exception.InsufficientBalanceException;
import com.osttra.orderservice.exception.ValidationException;
import com.osttra.orderservice.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testBuyAsset() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId("ABC");
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenReturn(orderMessage);
        OrderMessage om = orderController.buyAsset(new OrderDTO());
        Assertions.assertEquals("ABC", om.getId());
    }

    @Test
    public void testBuyAssetVException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(ValidationException.class);
        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> orderController.buyAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testBuyAssetIBException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(InsufficientBalanceException.class);
        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                () -> orderController.buyAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testBuyAssetANFException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(AssetNotFoundException.class);
        AssetNotFoundException thrown = assertThrows(
                AssetNotFoundException.class,
                () -> orderController.buyAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testBuyAssetAcNFException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(AccountNotFoundException.class);
        AccountNotFoundException thrown = assertThrows(
                AccountNotFoundException.class,
                () -> orderController.buyAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSellAsset() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId("ABC");
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenReturn(orderMessage);
        OrderMessage om = orderController.sellAsset(new OrderDTO());
        Assertions.assertEquals("ABC", om.getId());
    }

    @Test
    public void testSellAssetVException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(ValidationException.class);
        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> orderController.sellAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSellAssetIBException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(InsufficientBalanceException.class);
        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                () -> orderController.sellAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSellAssetANFException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(AssetNotFoundException.class);
        AssetNotFoundException thrown = assertThrows(
                AssetNotFoundException.class,
                () -> orderController.sellAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSellAssetAcNFException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(AccountNotFoundException.class);
        AccountNotFoundException thrown = assertThrows(
                AccountNotFoundException.class,
                () -> orderController.sellAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSwitchAsset() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId("ABC");
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenReturn(orderMessage);
        OrderMessage om = orderController.switchAsset(new OrderDTO());
        Assertions.assertEquals("ABC", om.getId());
    }

    @Test
    public void testSwitchAssetVException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(ValidationException.class);
        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> orderController.switchAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSwitchAssetIBException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(InsufficientBalanceException.class);
        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                () -> orderController.switchAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSwitchAssetANFException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(AssetNotFoundException.class);
        AssetNotFoundException thrown = assertThrows(
                AssetNotFoundException.class,
                () -> orderController.switchAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testSwitchAssetAcNFException() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        when(orderService.order(any(OrderDTO.class), any(OrderType.class))).thenThrow(AccountNotFoundException.class);
        AccountNotFoundException thrown = assertThrows(
                AccountNotFoundException.class,
                () -> orderController.switchAsset(new OrderDTO()),
                "Expected buyAsset() to throw, but it didn't"
        );
    }
}
