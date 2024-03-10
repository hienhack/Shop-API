package com.example.tutorial.service.Delivery;

import com.example.tutorial.entity.Address;
import com.example.tutorial.entity.Order;
import com.example.tutorial.enumeration.DeliveryProviderName;
import org.springframework.stereotype.Component;

@Component
public class ViettelPostDeliveryProvider implements DeliveryProvider {
    @Override
    public int getFee(Address address, float weight) {
        return 0;
    }

    @Override
    public void createOrder(Order order) {

    }

    @Override
    public DeliveryProviderName providerName() {
        return DeliveryProviderName.VIETTEL_POST;
    }
}
