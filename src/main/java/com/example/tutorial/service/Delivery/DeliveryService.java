package com.example.tutorial.service.Delivery;

import com.example.tutorial.entity.Address;
import com.example.tutorial.entity.Order;
import com.example.tutorial.enumeration.DeliveryProviderName;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final Map<DeliveryProviderName, DeliveryProvider> deliveryServices;

    public int getFee(DeliveryProviderName serviceName, Address address, float weight) {
        return getService(serviceName).getFee(address, weight);
    }

    /**
     * Get expected delivery date and fee
     * @param addressId id of receiver address
     * @param weight total weight of the order
     * @return delivery service provider name, expected date and fee
     */
    public Map<DeliveryProviderName, Integer> getDeliveryInfo(Integer addressId, int weight) {
        // Todo: implement this method
        return null;
    }

    public  void createOrder(DeliveryProviderName serviceName, Order order) {
        getService(serviceName).createOrder(order);
    }

    private DeliveryProvider getService(DeliveryProviderName serviceName) {
        if (!deliveryServices.containsKey(serviceName)) {
            throw new BusinessException("Delivery service not supported");
        }

        return deliveryServices.get(serviceName);
    }
}
