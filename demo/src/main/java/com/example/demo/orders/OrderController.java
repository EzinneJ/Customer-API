package com.example.demo.orders;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> orderList(Order orders) {
        List<Order> orders1 = orderService.listOfOrders();
        return new ResponseEntity<>(orders1, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> oneOrder(@PathVariable Long orderId) {
        Order orderById = orderService.findOrderById(orderId);
        return new ResponseEntity<>(orderById, HttpStatus.FOUND);

    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        Order updatedOrder = orderService.updateOrder(order, orderId);
        return  new ResponseEntity<>(updatedOrder, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderService.createNewOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

}
