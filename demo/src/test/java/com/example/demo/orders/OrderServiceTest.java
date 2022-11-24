package com.example.demo.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    public static final long ORDER_ID = 1L;
    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository);
    }

    @Test
    void givenListOfOrdersWhenValidThenReturnOrders() {
        //given
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderRepository.save(order);
        //when
        orderService.listOfOrders();
        //then
        verify(orderRepository, times(1)).findAll();
        assertThat(order.getDescription()).isEqualTo("red bag");
    }

    @Test
    void givenIdWhenValidThenReturnOrder() {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderRepository.save(order);
        //when
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Order orderById = orderService.findOrderById(ORDER_ID);
        //then
        verify(orderRepository, times(1)).findById(anyLong());
        assertThat(orderById.setPrice(order.getPrice())).isEqualTo("1000.00");

    }

    @Test
    void givenDetailWhenUpdatingThenReturnNewDetails() {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderRepository.save(order);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Order newOrderDetail = orderService.findOrderById(ORDER_ID);
        orderService.updateOrder(newOrderDetail, ORDER_ID);
        order.setDescription(newOrderDetail.setDescription("Green bag"));
        order.setQuantity(newOrderDetail.setQuantity(2));
         orderRepository.save(newOrderDetail);

         verify(orderRepository, times(2)).findById(anyLong());
         verify(orderRepository, times(4)).save(any());
        assertThat(newOrderDetail.getQuantity()).isEqualTo(2);
        assertThat(newOrderDetail.getDescription()).isEqualTo("Green bag");
        assertFalse(newOrderDetail.getDescription().startsWith("red"));
    }

    @Test
    void givenOrderWhenSavingThenReturnOrder() {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderRepository.save(order);

        when(orderRepository.save(any())).thenReturn(any());
        Order newOrder = orderService.createNewOrder(order);

        verify(orderRepository, times(2)).save(any());

    }

    @Test
    void givenIdWhenDeletingThenReturnOk() {
        Order order = new Order();
        order.setId(ORDER_ID);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderRepository.save(order);

        orderService.cancelOrder(ORDER_ID);
        Optional<Order> optionalOrder = orderRepository.findById(ORDER_ID);

        verify(orderRepository, times(1)).deleteById(anyLong());
        assertTrue(optionalOrder.isEmpty());
    }


}