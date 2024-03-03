package com.example.tutorial.service;

import com.example.tutorial.dto.Payment.Momo.GetPaymentURLRequest;
import com.example.tutorial.dto.Payment.Momo.GetPaymentURLResponse;
import com.example.tutorial.dto.Payment.Momo.IPNRequest;
import com.example.tutorial.dto.Payment.Momo.IPNResponse;
import com.example.tutorial.entity.Order;
import com.example.tutorial.entity.Payment;
import com.example.tutorial.event.PaymentSuccessEvent;
import com.example.tutorial.repository.OrderRepository;
import com.example.tutorial.repository.PaymentRepository;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Service
public class PaymentService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public String getPaymentURL(Integer orderId) {
        String url = "https://test-payment.momo.vn/v2/gateway/api/create";
        String secretKey = ""; // Load from application properties
        String accessKey = ""; // Load from application properties

        Order order = orderRepository.findById(orderId).orElseThrow();
        GetPaymentURLRequest requestBody = new GetPaymentURLRequest();
        requestBody.setPartnerCode("");
        requestBody.setPartnerName("Waldez shop"); // not required
        requestBody.setStoreId(""); // not required
        requestBody.setRequestType("");
        requestBody.setIpnUrl("");
        requestBody.setRedirectUrl(""); // finish later
        requestBody.setOrderId(orderId.toString());
        requestBody.setAmount(order.getTotalCost());
        requestBody.setLang("en");
        requestBody.setOrderInfo("Waldez shop order " + orderId);
        requestBody.setRequestId(order.getPayment().getId().toString());
        requestBody.setExtraData(""); // not required
        requestBody.encodeSignature(secretKey, accessKey);


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<GetPaymentURLRequest> request = new HttpEntity<>(requestBody, headers);
        GetPaymentURLResponse response = restTemplate.postForObject(url, request, GetPaymentURLResponse.class);

        // Error handling

        return response.getPayUrl();
    }


    public IPNResponse handlePaymentResult(IPNRequest result) {
        // Todo
        // Check valid data using the signature field

        if (result.getResultCode() == 0) {
            try {
                Payment payment = paymentRepository.findById(result.getRequestId()).orElseThrow();
                payment.setIsPaid(true);
                payment.setMomoTransId(result.getTransld());
                Timestamp created = new Timestamp(result.getResponseTime());
                payment.setPaid(created.toLocalDateTime());

                paymentRepository.save(payment);

                // Call order service to update order's state
                eventPublisher.publishEvent(new PaymentSuccessEvent(Integer.valueOf(result.getOrderld())));
                IPNResponse response = new IPNResponse();
                response.setPartnerCode("");
                // response.set

            } catch (Exception e) {
                // Todo
                // Refund when error happens
            }
        } else {
            // Todo
            // Error handling here
        }
        return null;
    }

    public void refund() {

    }
}
