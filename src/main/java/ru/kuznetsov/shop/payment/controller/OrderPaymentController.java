package ru.kuznetsov.shop.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kuznetsov.shop.payment.service.PaymentsProcessingService;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class OrderPaymentController {

    private final PaymentsProcessingService paymentsProcessingService;

    @PostMapping("/success")
    public ResponseEntity<Boolean> success(@RequestParam Long orderId) {
        if (paymentsProcessingService.processPaymentSuccessStatus(orderId)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
