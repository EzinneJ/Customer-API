package com.example.demo.customers;

import com.example.demo.delivery.Address;
import com.example.demo.orders.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Entity(name = "Customer")
public class Customer {
   @Id
   @Column(name = "customer_id")
   @SequenceGenerator(name = "customer_sequence",
           sequenceName = "customer_sequence",
           allocationSize = 1)
    private Long id;
   @Column(name = "name")
   private String name;
   @Column(name = "email")
   private String email;

   @OneToOne(mappedBy = "customer", cascade =  CascadeType.ALL)
   @JoinTable(name = "my_order")
   private Order order;

   @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
   @JoinTable(name = "address")
   private Address address;

   public Customer(String name, String email, Order order, Address address) {
      this.name = name;
      this.email = email;
      this.order = order;
      this.address = address;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String setName(String name) {
      this.name = name;
      return name;
   }

   public String setEmail(String email) {
      this.email = email;
      return email;
   }

   public Order setOrder(Order orders) {
      this.order = (Order) orders;
      return orders;
   }

   public Address setAddress(Address address) {
      this.address = address;
      return address;
   }
}
