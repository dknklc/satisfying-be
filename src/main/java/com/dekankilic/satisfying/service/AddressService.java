package com.dekankilic.satisfying.service;

import com.dekankilic.satisfying.model.Address;
import com.dekankilic.satisfying.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;


    public Address saveAddress(Address address){
        return addressRepository.save(address);
    }
}
