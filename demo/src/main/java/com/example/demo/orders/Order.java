package com.example.demo.orders;

import com.example.demo.customers.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@ToString
@Entity(name = "my_Order")
@Table(name = "my_order")
@NoArgsConstructor
public class Order {
    @Id
//    @SequenceGenerator(name = "order_sequence",
//            sequenceName = "order_sequence",
//            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @Column(name = "order_id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Order(String description, Integer quantity, BigDecimal price) {
        this.description = description;
        this.quantity = quantity;
        this.price = price;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String setDescription(String description) {
        this.description = description;
        return description;
    }

    public Integer setQuantity(Integer quantity) {
        this.quantity = quantity;
        return quantity;
    }

    public BigDecimal setPrice(BigDecimal price) {
        this.price = price;
        return price;
    }

    public Customer setCustomer(Customer customer) {
        this.customer = customer;
        return customer;
    }
}
