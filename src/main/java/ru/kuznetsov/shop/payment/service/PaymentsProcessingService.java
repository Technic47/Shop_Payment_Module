package ru.kuznetsov.shop.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuznetsov.shop.represent.contract.order.OrderStatusContract;
import ru.kuznetsov.shop.represent.dto.order.OrderStatusDto;

import java.time.LocalDateTime;

import static ru.kuznetsov.shop.represent.enums.OrderStatusType.READY;

@Service
@RequiredArgsConstructor
public class PaymentsProcessingService {

    private final OrderStatusContract orderStatusService;

    public Boolean processPaymentSuccessStatus(Long orderId) {
        try {
            orderStatusService.create(
                    new OrderStatusDto(
                            READY,
                            "Payment service",
                            "Payment received " + LocalDateTime.now(),
                            orderId
                    )
            );

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
