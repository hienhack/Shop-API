package com.example.tutorial.service;

import com.example.tutorial.dto.User.UserCreationDTO;
import com.example.tutorial.dto.User.UserDTO;
import com.example.tutorial.entity.Address;
import com.example.tutorial.entity.User;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.AddressRepository;
import com.example.tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String account) {
        return userRepository.findUserByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException(account));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public User loadUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO create(UserCreationDTO userCreationDTO) {
        // Checking valid password: length, including special characters,...
        // Checking valid email
       // Checking valid phone

        // Checking existing email, phone
        if (userRepository.existsByEmail(userCreationDTO.getEmail())) {
            throw new BusinessException("Email existed, try another one");
        } else if (userRepository.existsByPhone(userCreationDTO.getPhone())) {
            throw new BusinessException("Phone existed, try another one");
        }

        User user = new User();
        user.setName(userCreationDTO.getName());
        user.setPhone(userCreationDTO.getPhone());
        user.setEmail(userCreationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));

        user.getRoles().addAll(userCreationDTO.getRoles());
        user.getRoles().forEach(userRole -> userRole.setUser(user));

        return new UserDTO(userRepository.save(user));
    }

    public void addAddress(User user, Address address) {
        address.setCustomer(user);
        addressRepository.save(address);
    }

    public void removeAddress(User customer, Integer addressId) {
        if (!addressRepository.existsByIdAndCustomerId(addressId, customer.getId()))
            throw new BusinessException("Not found address with id = " + addressId + "in user's addresses");

        addressRepository.deleteById(addressId);
    }

    public Address updateAddress(User customer, Integer addressId, Address newAddress) {
        if (!addressRepository.existsByIdAndCustomerId(addressId, customer.getId()))
            throw new BusinessException("Not found address with id = " + addressId + "in user's addresses");

        newAddress.setId(addressId);
        newAddress.setCustomer(customer);

        return addressRepository.save(newAddress);
    }
}
