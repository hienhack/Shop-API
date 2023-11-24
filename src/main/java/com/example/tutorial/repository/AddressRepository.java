package com.example.tutorial.repository;

import com.example.tutorial.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    public boolean existsByIdAndCustomerId(Integer addressId, Integer customerId);
    public Optional<Address> findAddressByIdAndCustomerId(Integer addressId, Integer customerId);
}
