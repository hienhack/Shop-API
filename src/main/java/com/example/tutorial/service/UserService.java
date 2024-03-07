package com.example.tutorial.service;

import com.example.tutorial.dto.User.UserCreationDTO;
import com.example.tutorial.dto.User.UserDTO;
import com.example.tutorial.dto.User.UserUpdateDTO;
import com.example.tutorial.entity.Address;
import com.example.tutorial.entity.Cart;
import com.example.tutorial.entity.User;
import com.example.tutorial.entity.UserRole;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.AddressRepository;
import com.example.tutorial.repository.CartRepository;
import com.example.tutorial.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String account) {
        return userRepository.findUserByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException(account));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public UserDTO getUser(Integer id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT.value(), "User not found"));

        return new UserDTO(found);
    }

    public User loadUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NO_CONTENT.value(), "User not found"));
    }

    @Transactional(rollbackOn = { Exception.class })
    public UserDTO create(UserCreationDTO userCreationDTO) {
        if (userRepository.existsByPhone(userCreationDTO.getPhone())) {
            throw new BusinessException("Phone existed, try another one");
        }

        User user = new User();
        user.setName(userCreationDTO.getName());
        user.setPhone(userCreationDTO.getPhone());
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));

        user.getRoles().addAll(userCreationDTO.getRoles());
        user.getRoles().forEach(userRole -> userRole.setUser(user));
        var createdUser = userRepository.save(user);

        // Create customer cart
        if (isCustomer(createdUser)) {
            createUserCart(createdUser);
        }

        return new UserDTO(userRepository.save(user));
    }

    public UserDTO updateUser(User user, UserUpdateDTO userUpdateDTO) {
        user.setEmail(userUpdateDTO.getEmail());
        user.setName(userUpdateDTO.getName());

        return new UserDTO(userRepository.save(user));
    }

    public void addAddress(User user, Address address) {
        address.setCustomer(user);
        addressRepository.save(address);
    }

    public void removeAddress(User customer, Integer addressId) {
        if (!addressRepository.existsByIdAndCustomer_Id(addressId, customer.getId()))
            throw new BusinessException("Not found address with id = " + addressId + "in user's addresses");

        addressRepository.deleteById(addressId);
    }

    public Address updateAddress(User customer, Integer addressId, Address newAddress) {
        if (!addressRepository.existsByIdAndCustomer_Id(addressId, customer.getId()))
            throw new BusinessException("Not found address with id = " + addressId + "in user's addresses");

        newAddress.setId(addressId);
        newAddress.setCustomer(customer);

        return addressRepository.save(newAddress);
    }

    private void createUserCart(User customer) {
        var cart = new Cart();
        cart.setCustomer(customer);
        cartRepository.save(cart);
    }

    private boolean isCustomer(User user) {
        for (UserRole userRole : user.getRoles()) {
            if (userRole.getRole().name().split("_")[0].equals("USER")) {
                return true;
            }
        }

        return false;
    }
}
