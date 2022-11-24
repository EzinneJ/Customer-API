package com.example.demo.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> getAddressList() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId).get();
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Long addressId, Address newAddress){
    return addressRepository.findById(addressId)
                .map(existingAddress -> {
                    existingAddress.setPostcode(newAddress.getPostcode());
                    existingAddress.setApartmentNumber(newAddress.getApartmentNumber());
                    existingAddress.setStreetNumber(newAddress.getStreetNumber());
                    existingAddress.setCity(newAddress.getCity());
                    existingAddress.setState(newAddress.getState());
                    existingAddress.setCountry(newAddress.getCountry());
                    return addressRepository.save(existingAddress);
                }).orElseGet(() -> {
                   return addressRepository.save(newAddress);
            });
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}
