package com.example.tutorial.repository;

import com.example.tutorial.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    public boolean existsByIdAndCustomer_Id(Integer addressId, Integer customerId);
    public List<Address> findAddressByCustomer_Id(Integer id);
    public Optional<Address> findAddressByIdAndCustomerId(Integer addressId, Integer customerId);
}
