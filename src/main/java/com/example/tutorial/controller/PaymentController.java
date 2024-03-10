package com.example.tutorial.controller;

import com.example.tutorial.dto.Payment.Momo.IPNRequest;
import com.example.tutorial.dto.Payment.Momo.IPNResponse;
import com.example.tutorial.dto.Response.ResponseDTO;
import com.example.tutorial.entity.User;
import com.example.tutorial.service.PaymentService;
import com.example.tutorial.util.AuthenticationHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{paymentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseDTO<String> getMomoPaymentURL(Authentication authentication, @PathVariable(name = "paymentId") Integer paymentId) {
        User customer = AuthenticationHelper.getUser(authentication);
        return ResponseDTO.of(paymentService.getMomoPaymentURL(customer, paymentId));
    }

    @PostMapping("/result")
    public ResponseEntity<IPNResponse> handleMomoPaymentResult(
            @RequestBody @Valid IPNRequest ipnRequest
    ) {
        paymentService.handleMockPaymentResult(ipnRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
