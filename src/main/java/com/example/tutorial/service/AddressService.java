package com.example.tutorial.service;

import com.example.tutorial.dto.Address.AddressDTO;
import com.example.tutorial.entity.Address;
import com.example.tutorial.entity.User;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressDTO> getAll(User user) {
        return addressRepository.findAddressByCustomer_Id(user.getId())
                .stream().map(AddressDTO::new).collect(Collectors.toList());
    }

    public AddressDTO create(User user, AddressDTO addressDTO) {
        var address = createAddressFromAddressDTO(user, addressDTO);
        return new AddressDTO(addressRepository.save(address));
    }

    public AddressDTO update(User user, Integer addressId, AddressDTO addressDTO) {
        if (!addressRepository.existsByIdAndCustomer_Id(addressId, user.getId())) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "Address not found");
        }

        var address = createAddressFromAddressDTO(user, addressDTO);
        address.setId(addressId);
        return new AddressDTO(addressRepository.save(address));
    }

    public void delete(User user, Integer addressId) {
        if (!addressRepository.existsByIdAndCustomer_Id(addressId, user.getId())) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "Address not found");
        }

        addressRepository.deleteById(addressId);
    }

    private Address createAddressFromAddressDTO(User user, AddressDTO addressDTO) {
        Address address = new Address();

        String a = addressDTO.getDistrict();

        address.setId(addressDTO.getId());
        address.setCustomer(user);
        address.setAddress(addressDTO.getAddress());
        address.setProvince(addressDTO.getProvince());
        address.setDistrict(addressDTO.getDistrict());
        address.setVillage(addressDTO.getVillage());
        address.setWard(addressDTO.getWard());
        address.setWard(addressDTO.getWard());

        return address;
    }
}
