package com.example.tutorial.service;

import com.example.tutorial.dto.Order.OrderCreationDTO;
import com.example.tutorial.dto.Order.OrderDTO;
import com.example.tutorial.dto.OrderDetail.OrderDetailCreationDTO;
import com.example.tutorial.entity.*;
import com.example.tutorial.enumeration.DeliveryProviderName;
import com.example.tutorial.enumeration.PaymentMethod;
import com.example.tutorial.enumeration.Size;
import com.example.tutorial.enumeration.OrderState;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.AddressRepository;
import com.example.tutorial.repository.OrderRepository;
import com.example.tutorial.repository.ProductRepository;
import com.example.tutorial.service.Delivery.DeliveryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final DeliveryService deliveryService;

    public Page<OrderDTO> adminGetAll(List<OrderState> states, Pageable pageRequest) {
        return orderRepository.findByStateIsIn(states, pageRequest).map(OrderDTO::new);
    }

    public Page<OrderDTO> userGetAll(User customer, List<OrderState> states, Pageable pageRequest) {
        return orderRepository.findByStateIsInAndCustomer_Id(states, customer.getId(), pageRequest).map(OrderDTO::new);
    }

    public OrderDTO adminGetDetail(Integer orderId) {
        Order result  = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND.value(), "Not found order with id = " + orderId));
        return new OrderDTO(result);
    }

    public OrderDTO userGetDetail(User customer, Integer orderId) {
        Order result = orderRepository.findOrderByIdAndCustomer_Id(orderId, customer.getId())
                .orElseThrow(() -> new BusinessException("Not found order with id = " + orderId));
        return new OrderDTO(result);
    }

    public int getOrderQuote(OrderCreationDTO orderDTO) {
        float weight = 0;
        int productsCost = 0;
        for (OrderDetailCreationDTO item : orderDTO.getOrderDetails()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BusinessException("Not found product with id = " + item.getProductId()));
            weight += item.getQuantity() * product.getWeight();
            productsCost += item.getQuantity() * product.getPrice();
        }

        Address address = addressRepository.findById(orderDTO.getDelivery().getAddressId())
                .orElseThrow(() -> new BusinessException("Address not found"));
        int deliveryFee =
                deliveryService.getFee(DeliveryProviderName.valueOf(orderDTO.getDelivery().getServiceName()), address, weight);
        return productsCost + deliveryFee;
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderDTO create(User customer, OrderCreationDTO orderDTO) {
        // Get products list, calculate total weight and total cost
        Order order = new Order();
        order.setCreated(LocalDateTime.now());
        order.setCustomer(customer);
        order.setState(OrderState.CONFIRMING);

        float weight = 0;
        int productsCost = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailCreationDTO item : orderDTO.getOrderDetails()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BusinessException("Not found product with id = " + item.getProductId()));
            int inStock = product.getInStock(item.getType(), Size.valueOf(item.getSize()));
            if (item.getQuantity() > inStock) {
                throw new BusinessException("Not enough product(s) with id = " +
                        item.getProductId() + ", type = " + item.getType() + " and size = " + item.getSize());
            }

            weight += item.getQuantity() * product.getWeight();
            productsCost += item.getQuantity() * product.getPrice();
            orderDetails.add(new OrderDetail(order, product, item.getType(),
                    Size.valueOf(item.getSize()), product.getPrice(), item.getQuantity()));

            product.setInStock(item.getType(), Size.valueOf(item.getSize()), inStock - item.getQuantity());
            productRepository.save(product);
        }
        order.setOrderDetails(orderDetails);
        order.setProductsCost(productsCost);

        Address address = addressRepository.findById(orderDTO.getDelivery().getAddressId())
                .orElseThrow(() -> new BusinessException("Address not found"));
        int deliveryFee =
                deliveryService.getFee(DeliveryProviderName.valueOf(orderDTO.getDelivery().getServiceName()), address, weight);
        order.setDelivery(new Delivery(address.toString(), orderDTO.getDelivery().getName(),
                orderDTO.getDelivery().getPhone(), weight,
                DeliveryProviderName.valueOf(orderDTO.getDelivery().getServiceName()), deliveryFee));
        order.setTotalCost(productsCost + deliveryFee);
        order.setPayment(new Payment(productsCost + deliveryFee,
                PaymentMethod.valueOf(orderDTO.getPaymentMethod()), customer));

        Order saved = orderRepository.save(order);
        return new OrderDTO(saved);
    }

    /**
     * Admin checkout and create delivery service order
     * @param orderId id of the order
     */
    @Transactional(rollbackOn = Exception.class)
    public OrderDTO checkout(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Not found order with id = " + orderId));

        order.setCheckedOut(true);
        Order updated = orderRepository.save(order);
        deliveryService.createOrder(order.getDelivery().getServiceName(), updated);
        return new OrderDTO(updated);
    }

    /**
     * Mock cancel method for testing only
     * @param customer authenticated user from authentication context
     * @param orderId id of the order
     */
    public void cancelOrder(User customer, Integer orderId) {
        // Mock
        Order order = orderRepository.findOrderByIdAndCustomer_Id(orderId, customer.getId())
                .orElseThrow(() -> new BusinessException("Not found order with id = " + orderId));

        order.setState(OrderState.CANCELLED);
        orderRepository.save(order);
    }

    /**
     * Delete the order with id given. For testing only
     * @param orderId id of the order
     */
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}