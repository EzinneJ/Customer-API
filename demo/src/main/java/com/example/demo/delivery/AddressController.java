package com.example.demo.delivery;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping(path = "/api/v1/addresses")
@RestController
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAddressList() {
        List<Address> addressList = addressService.getAddressList();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long addressId) {
        Address addressById = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressById, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Address> saveAddress(@RequestBody Address address) {
        Address savedAddress = addressService.saveAddress(address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long addressId, @RequestBody Address newAddress){
        Address updatedAddress = addressService.updateAddress(addressId, newAddress);
        return new ResponseEntity<>(updatedAddress, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
