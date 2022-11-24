package com.example.demo.delivery;

import com.example.demo.customers.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "Address")
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String postcode;
    private String apartmentNumber;
    private String streetNumber;
    private String city;
    private String state;
    private String country;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address(String postcode, String apartmentNumber, String streetNumber, String city, String state, String country) {
        this.postcode = postcode;
        this.apartmentNumber = apartmentNumber;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String setPostcode(String postcode) {
        this.postcode = postcode;
        return postcode;
    }

    public String setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
        return apartmentNumber;
    }

    public String setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
        return streetNumber;
    }

    public String setCity(String city) {
        this.city = city;
        return city;
    }

    public String setState(String state) {
        this.state = state;
        return state;
    }

    public String setCountry(String country) {
        this.country = country;
        return country;
    }

    public Customer setCustomer(Customer customer) {
        this.customer = customer;
        return customer;
    }
}
