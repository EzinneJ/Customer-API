package com.example.demo.customers;

import com.example.demo.orders.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> listOfCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    public Customer updateCustomerDetails(Long customerId, Customer newDetails) {
        return customerRepository.findById(customerId)
                .map(existingDetails -> {
                    existingDetails.setName(newDetails.getName());
                    existingDetails.setEmail(newDetails.getEmail());
                    existingDetails.setOrder((Order) newDetails.getOrder());
                    existingDetails.setAddress(newDetails.getAddress());
                    return customerRepository.save(existingDetails);
                }).orElseGet(() -> {
                    newDetails.setId(newDetails.getId());
                    return customerRepository.save(newDetails);
                });
    }

    public Customer createCustomer(Customer customer){
       return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

}
