package com.example.demo;

import com.example.demo.customers.Customer;
import com.example.demo.delivery.Address;
import com.example.demo.orders.Order;
import com.example.demo.orders.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(OrderRepository orderRepository) {
//		return args -> {
//			Order gucci_bag = new Order("Gucci Bag", 1, new BigDecimal(6000));
//			orderRepository.save(gucci_bag);
//			Customer customerOrder = new Customer( "Ebube", "ebube@gmail.com",
//					new Order("Utaba", 1, new BigDecimal(500)),
//					new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" ));
//		};
//	}

}
