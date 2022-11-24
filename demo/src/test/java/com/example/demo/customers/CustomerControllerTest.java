package com.example.demo.customers;

import com.example.demo.delivery.Address;
import com.example.demo.orders.Order;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;
    @Autowired
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }

    @Test
    void canGetCustomers() throws Exception {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" ));
        customerService.createCustomer(customer);

        this.mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andReturn();

   verify(customerService, times(1)).listOfCustomers();
    }

    @Test
    void canGetCustomerById() throws Exception {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria"));
        customerService.createCustomer(customer);

        this.mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isFound())
                .andReturn();

        verify(customerService, times(1)).getCustomerById(anyLong());
    }

    @Test
    void canCreateCustomer() throws Exception {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria"));
        customerService.createCustomer(customer);

        this.mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                        .andExpect(status().isCreated())
                        .andReturn();

        verify(customerService, times(2)).createCustomer(any());
    }

    @Test
    void canUpdateCustomer() throws Exception {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria"));
        customerService.createCustomer(customer);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);
        Customer updatedCustomer = customerService.getCustomerById(1L);
        customer.setOrder(updatedCustomer.setOrder(new Order("Utaba", 2, new BigDecimal(1500.00))));
        customerService.updateCustomerDetails(1L, updatedCustomer);

        this.mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isAccepted())
                .andReturn();

        verify(customerService, times(2)).updateCustomerDetails(anyLong(), any());
        assertThat(updatedCustomer.getAddress()).hasFieldOrPropertyWithValue("city", "Mpape");
        assertThat(updatedCustomer.getOrder()).hasFieldOrPropertyWithValue("description", "Utaba");
    }

    @Test
    void canDeletCustomer() throws Exception {
        Customer customer = new Customer(1L, "Ebube", "ebube@gmail.com",
                new Order("Utaba", 1, new BigDecimal(500)),
                new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria"));
        customerService.createCustomer(customer);

        this.mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(customerService, times(1)).deleteCustomer(anyLong());
        assertTrue(customerService.listOfCustomers().isEmpty());
    }

}