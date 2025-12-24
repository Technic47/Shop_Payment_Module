package ru.kuznetsov.shop.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kuznetsov.shop.payment.service.PaymentsProcessingService;
import ru.kuznetsov.shop.represent.dto.order.OrderStatusDto;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class OrderPaymentController {

    private final PaymentsProcessingService paymentsProcessingService;

    @PostMapping("/success")
    public ResponseEntity<OrderStatusDto> success(@RequestParam Long orderId) {
        return ResponseEntity.ok(paymentsProcessingService.processPaymentSuccessStatus(orderId));
    }
}
