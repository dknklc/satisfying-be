package com.dekankilic.satisfying.repository;

import com.dekankilic.satisfying.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
