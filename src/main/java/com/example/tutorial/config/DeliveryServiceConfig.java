package com.example.tutorial.config;

import com.example.tutorial.enumeration.DeliveryProviderName;
import com.example.tutorial.service.Delivery.DeliveryProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DeliveryServiceConfig {
    private final List<DeliveryProvider> deliveryProviders;

    @Bean
    public Map<DeliveryProviderName, DeliveryProvider> loadDeliveryServiceProviders() {
        Map<DeliveryProviderName, DeliveryProvider> result = new EnumMap<>(DeliveryProviderName.class);
        for (var deliveryProvider : deliveryProviders) {
            result.put(deliveryProvider.providerName(), deliveryProvider);
        }
        return result;
    }

}
