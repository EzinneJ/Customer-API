package com.example.demo.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @Autowired
    private OrderController orderController;
    @Mock
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
    }

    @Test
    void getOrderList() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDescription("utaba");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderService.createNewOrder(order);

        this.mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andReturn();

        verify(orderService, times(1)).listOfOrders();
        assertThat(order.getDescription()).isEqualTo("utaba");
    }

    @Test
    void getOrderById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderService.createNewOrder(order);

        this.mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isFound())
                .andReturn();

        verify(orderService, times(1)).findOrderById(1L);
        assertThat(order.getDescription()).isEqualTo("red bag");
    }


    @Test
    void createOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderService.createNewOrder(order);

        this.mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andReturn();

        verify(orderService, times(1)).createNewOrder(order);
        assertThat(order.getDescription()).isEqualTo("red bag");
    }

    @Test
    void updateOrderById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderService.createNewOrder(order);

        when(orderService.findOrderById(anyLong())).thenReturn(order);

        Order updatedDetails = orderService.findOrderById(1L);
        order.setDescription(updatedDetails.setDescription("Utaba"));
        orderService.updateOrder(updatedDetails, 1L);

        this.mockMvc.perform(put("/api/v1/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isAccepted())
                .andReturn();

        verify(orderService, times(1)).updateOrder(updatedDetails,1L);
        assertThat(updatedDetails.getDescription()).isEqualTo("Utaba");
    }

    @Test
    void deleteOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDescription("red bag");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1000.00"));
        orderService.createNewOrder(order);

        this.mockMvc.perform(delete("/api/v1/orders/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(orderService, times(1)).cancelOrder(1L);
    }

}