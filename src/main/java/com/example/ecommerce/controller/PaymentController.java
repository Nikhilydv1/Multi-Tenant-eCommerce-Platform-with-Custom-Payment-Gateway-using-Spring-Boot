package com.example.ecommerce.controller;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.dto.PaymentResponse;
import com.example.ecommerce.entity.Payment;
import com.example.ecommerce.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            Payment payment = paymentService.processPayment(paymentRequest.getAmount(), paymentRequest.getCurrency());
            PaymentResponse paymentResponse = new PaymentResponse(payment.getStripePaymentId(), "Success");
            return ResponseEntity.ok(paymentResponse);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(new PaymentResponse(null, "Failed: " + e.getMessage()));
        }
    }
}
