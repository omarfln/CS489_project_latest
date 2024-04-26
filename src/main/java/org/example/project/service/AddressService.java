package org.example.project.service;

import org.example.project.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {
    Address addAddress(Address address);
    void deleteAddressById(Long id);
    Address updateAddress(Address address);
    List<Address> getAllAddresses();
    Optional<Address> getAddressById(Long id);
}