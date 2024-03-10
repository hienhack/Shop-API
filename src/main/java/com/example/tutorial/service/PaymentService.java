package com.example.tutorial.service;

import com.example.tutorial.dto.Payment.Momo.GetPaymentURLRequest;
import com.example.tutorial.dto.Payment.Momo.GetPaymentURLResponse;
import com.example.tutorial.dto.Payment.Momo.IPNRequest;
import com.example.tutorial.dto.Payment.Momo.IPNResponse;
import com.example.tutorial.entity.Order;
import com.example.tutorial.entity.Payment;
import com.example.tutorial.entity.User;
import com.example.tutorial.enumeration.PaymentMethod;
import com.example.tutorial.event.PaymentSuccessEvent;
import com.example.tutorial.exception.BusinessException;
import com.example.tutorial.repository.OrderRepository;
import com.example.tutorial.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final Environment env;
    private final ApplicationEventPublisher eventPublisher;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public String getMomoPaymentURL(User customer, Integer paymentId) {
        Payment payment = paymentRepository.findByIdAndMethodAndCustomer_Id(paymentId, PaymentMethod.MOMO, customer.getId())
                .orElseThrow(() -> new BusinessException("Payment not found"));

//        String url = env.getProperty("payment.momo.url");
//        String secretKey = env.getProperty("payment.momo.secret_key");
//        String accessKey = env.getProperty("payment.momo.access_key");
//
//        GetPaymentURLRequest requestBody = new GetPaymentURLRequest();
//        requestBody.setPartnerCode("");
//        requestBody.setPartnerName("Waldez shop"); // not required
//        requestBody.setStoreId(""); // not required
//        requestBody.setRequestType("");
//        requestBody.setIpnUrl(""); // server end point that handles payment result
//        requestBody.setRedirectUrl(""); // finish later
//        requestBody.setOrderId(orderId.toString());
//        requestBody.setAmount(order.getTotalCost());
//        requestBody.setLang("en");
//        requestBody.setOrderInfo("Waldez shop order " + orderId);
//        requestBody.setRequestId(order.getPayment().getId().toString());
//        requestBody.setExtraData(""); // not required
//        requestBody.encodeSignature(secretKey, accessKey);
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<GetPaymentURLRequest> request = new HttpEntity<>(requestBody, headers);
//        GetPaymentURLResponse response = restTemplate.postForObject(url, request, GetPaymentURLResponse.class);

        // Error handling

        // Mock
        return "https://example.com/payment?id=sdf";
    }


    public IPNResponse handleMockPaymentResult(IPNRequest result) {
//        // Todo: Check if data is valid using signature field
//
//        if (result.getResultCode() == 0) {
//            try {
//                Payment payment = paymentRepository.findById(result.getRequestId()).orElseThrow();
//                payment.setIsPaid(true);
//                payment.setMomoTransId(result.getTransld());
//                Timestamp created = new Timestamp(result.getResponseTime());
//                payment.setPaid(created.toLocalDateTime());
//
//                paymentRepository.save(payment);
//
//                IPNResponse response = new IPNResponse();
//                response.setPartnerCode("");
//                // response.set
//
//            } catch (Exception e) {
//                // Todo: Refund when error happens
//                // refund();
//            }
//        } else {
//            // Todo
//            // Error handling here
//        }
//        return null;

        if (result.getStatus().equals(IPNRequest.Status.Success.name())) {
            Payment payment = paymentRepository.findById(Integer.valueOf(result.getPaymentId())).orElseThrow();
            payment.setIsPaid(true);
            payment.setPaid(LocalDateTime.now());
            paymentRepository.save(payment);
        }
        return null;
    }

//    public void mockSuccessPayment(Integer paymentId) {
//        Payment payment = paymentRepository.findById(paymentId)
//                .orElseThrow(() -> new BusinessException("Not found payment with id = " + paymentId));
//
//        payment.setPaid(LocalDateTime.now());
//        payment.setIsPaid(true);
//
//        paymentRepository.save(payment);
//    }

    private void refund() {
        // Todo: Implement this function
    }
}
