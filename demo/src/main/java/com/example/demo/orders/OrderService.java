package com.example.demo.orders;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> listOfOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }


    public Order createNewOrder(Order order) {
       return orderRepository.save(order);
    }

    public Order updateOrder(Order newOrder, Long orderId) {
        return orderRepository.findById(orderId)
                .map(existingOrder -> {
                    existingOrder.setDescription(newOrder.getDescription());
                    existingOrder.setQuantity(newOrder.getQuantity());
                    existingOrder.setPrice(newOrder.getPrice());
                    return orderRepository.save(existingOrder);
                }).orElseGet(() -> {
                    newOrder.setId(orderId);
                    return orderRepository.save(newOrder);
                });
    }

    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
