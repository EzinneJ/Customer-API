package com.example.demo.delivery;

import com.example.demo.customers.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressService = new AddressService(addressRepository);
    }

    @Test
    void getAddressList() {
        Address address = new Address("1011", "no. 5", "16", "Mpape", "Abuja", "Ngeria" );
        addressRepository.save(address);

        List<Address> addressList = addressService.getAddressList();
        addressList.add(address);

        verify(addressRepository, times(1)).findAll();
        assertThat(addressList.size()).isOne();
    }

    @Test
    void getAddressById() {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressRepository.save(address);

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        Address addressById = addressService.getAddressById(1L);

        verify(addressRepository, times(1)).findById(anyLong());
        assertThat(addressById).hasFieldOrProperty("postcode");
    }

    @Test
    void saveAddress() {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressRepository.save(address);

        when(addressRepository.save(any())).thenReturn(any());
         addressService.saveAddress(address);

        verify(addressRepository, times(2)).save(any());
        assertThat(address.getCustomer()).isNotNull();
    }

    @Test
    void updateAddress() {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressRepository.save(address);

        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        Address newAddress = addressService.getAddressById(1L);
        address.setCity(newAddress.setCity("Java"));
        address.setCountry(newAddress.setCountry("SE"));
        addressService.updateAddress(1L, newAddress);

        assertThat(newAddress.getCity()).isEqualTo("Java");
        assertThat(newAddress.getState()).isEqualTo("Abuja");
    }

    @Test
    void deleteAddress() {
        Address address = new Address(
                1L, "1011", "no. 5", "16", "Mpape",
                "Abuja", "Ngeria", new Customer() );
        addressRepository.save(address);

        addressService.deleteAddress(1L);
        Optional<Address> deletedAddress = addressRepository.findById(1L);

        verify(addressRepository, times(1)).deleteById(anyLong());
        assertThat(deletedAddress).isEmpty();
    }
}