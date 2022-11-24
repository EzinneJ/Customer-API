package com.example.demo.delivery;

import com.example.demo.customers.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AddressControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Mock
    private AddressService addressService;

    @Autowired
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(addressService)).build();
    }

    @Test
    void getAddressList() throws Exception {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressService.saveAddress(address);

        this.mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().isOk())
                .andReturn();

        verify(addressService, times(1)).getAddressList();
    }

    @Test
    void getAddressById() throws Exception {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressService.saveAddress(address);

        this.mockMvc.perform(get("/api/v1/addresses/1"))
                .andExpect(status().isFound())
                .andReturn();

        verify(addressService, times(1)).getAddressById(anyLong());
    }

    @Test
    void saveAddress() throws Exception {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressService.saveAddress(address);

        this.mockMvc.perform(post("/api/v1/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isCreated())
                .andReturn();

        verify(addressService, times(2)).saveAddress(any());
    }

    @Test
    void updateAddress() throws Exception {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressService.saveAddress(address);

        when(addressService.getAddressById(anyLong())).thenReturn(address);
        Address updatedAddress = addressService.getAddressById(1L);
        address.setCountry(updatedAddress.setCountry("SE"));
        addressService.updateAddress(1L, updatedAddress);


        this.mockMvc.perform(put("/api/v1/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAddress)))
                .andExpect(status().isAccepted())
                .andReturn();

        verify(addressService, times(2)).updateAddress(anyLong(), any());
        assertThat(updatedAddress.getState()).isEqualTo("Abuja");
        assertThat(updatedAddress.getCountry()).isEqualTo("SE");
    }

    @Test
    void deleteAddress() throws Exception {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressService.saveAddress(address);

        this.mockMvc.perform(delete("/api/v1/addresses/1"))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(addressService, times(1)).deleteAddress(anyLong());
    }
}