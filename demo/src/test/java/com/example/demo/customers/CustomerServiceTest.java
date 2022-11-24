package com.example.demo.customers;

import com.example.demo.delivery.Address;
import com.example.demo.orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
     void canGetListOfCustomers() {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" ));
        customerRepository.save(customer);

        List<Customer> customers = customerService.listOfCustomers();
        customers.add(customer);

        verify(customerRepository, times(1)).findAll();
         assertThat(customer.getOrder()).hasFieldOrPropertyWithValue("description", "Utaba");
         assertThat(customers.size()).isOne();
    }

    @Test
        void canGetCustomerById() {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" ));
        customerRepository.save(customer);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        Customer customerById = customerService.getCustomerById(1L);

        verify(customerRepository, times(1)).findById(anyLong());
        assertThat(customerById.getAddress()).hasFieldOrPropertyWithValue("streetNumber", "16");
    }

    @Test
     void canUpdateCustomer() {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" ));
        customerRepository.save(customer);


    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Customer updateCustomer = customerService.getCustomerById(1L);
        customerService.updateCustomerDetails(1L, updateCustomer);
        customer.setName(updateCustomer.setName("Grace"));
        customer.setEmail(updateCustomer.setEmail("grace@gmail.com"));
        customerRepository.save(updateCustomer);

        verify(customerRepository, times(2)).findById(anyLong());
        assertThat(updateCustomer.getAddress()).hasFieldOrPropertyWithValue("city", "Mpape");
        assertThat(updateCustomer.getEmail()).isEqualTo("grace@gmail.com");
    }

    @Test
     void canSaveNewCustomer() {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" ));
        customerRepository.save(customer);

        when(customerRepository.save(any())).thenReturn(any());
        customerService.createCustomer(customer);

        verify(customerRepository, times(2)).save(any());
        assertThat(customer.getEmail()).isEqualTo("ebube@gmail.com");

    }

    @Test
    void canDeleteCustomer() {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" ));
        customerRepository.save(customer);

        customerService.deleteCustomer(1L);
        Optional<Customer> deletedCustomer = customerRepository.findById(1L);

        verify(customerRepository, times(1)).deleteById(anyLong());
        assertThat(deletedCustomer).isEmpty();
    }
}