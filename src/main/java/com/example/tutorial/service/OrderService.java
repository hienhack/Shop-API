package com.example.tutorial.service;

import com.example.tutorial.dto.Order.OrderCreationDTO;
import com.example.tutorial.dto.Order.OrderDTO;
import com.example.tutorial.dto.OrderDetail.OrderDetailCreationDTO;
import com.example.tutorial.entity.*;
import com.example.tutorial.event.PaymentSuccessEvent;
import com.example.tutorial.enumeration.OrderState;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.AddressRepository;
import com.example.tutorial.repository.OrderRepository;
import com.example.tutorial.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepositoryd;
    private final DeliveryService deliveryService;

    @Transactional
    public Object order(User customer, OrderCreationDTO orderDTO) {
        // Get products list, calculate the total weight and total cost
        Order order = new Order();
        order.setCreated(LocalDateTime.now());
        order.setCustomer(customer);
        order.setState(OrderState.CONFIRMING);
        order.setOrderDetails(new ArrayList<>());

        int weight = 0;
        int productsCost = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailCreationDTO item : orderDTO.getOrderDetails()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new BusinessException("Not found product with id = " + item.getProductId()));
            int inStock = product.getInStock(item.getType(), item.getSize());
            if (item.getQuantity() > inStock) {
                throw new BusinessException("Not enough product(s)");
            }

            weight += item.getQuantity() * product.getWeight();
            productsCost += item.getQuantity() * product.getPrice();
            orderDetails.add(new OrderDetail(order, product, item.getType(), item.getSize(), product.getPrice(), item.getQuantity()));
        }
        order.setOrderDetails(orderDetails);
        order.setProductsCost(productsCost);

        Address address = addressRepositoryd.findById(orderDTO.getDelivery().getAddressId())
                .orElseThrow(() -> new BusinessException("Address not found"));
        int deliveryFee = deliveryService.getFee(address.getId(), weight);
        order.setDelivery(new Delivery(address.toString(), orderDTO.getDelivery().getName(),
                orderDTO.getDelivery().getPhone(), weight, deliveryFee));
        order.setTotalCost(productsCost + deliveryFee);
        order.setPayment(new Payment(productsCost + deliveryFee, orderDTO.getPayment().getMethod(), customer));

        Order saved = orderRepository.save(order);

        return new OrderDTO(saved);
    }

    @EventListener
    public void paymentConfirmUpdate(PaymentSuccessEvent paymentSuccessEvent) {
        Order order = orderRepository.findById((Integer) paymentSuccessEvent.getSource()).orElseThrow();
        order.setState(OrderState.PACKING);
        orderRepository.save(order);
    }

    public void updateState(Integer orderId, OrderState state) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Not found order with id = " + orderId));

        order.setState(state);
    }
}


// Call delivery provider api to get...
// Call payment api if user chooses credit card method for payment