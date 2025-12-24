package ru.kuznetsov.shop.payment.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.kuznetsov.shop.represent.contract.order.OrderStatusContract;
import ru.kuznetsov.shop.represent.dto.order.OrderStatusDto;

import java.time.LocalDateTime;

import static ru.kuznetsov.shop.represent.enums.OrderStatusType.AWAIT_PAYMENT;
import static ru.kuznetsov.shop.represent.enums.OrderStatusType.READY;

@Service
@RequiredArgsConstructor
public class PaymentsProcessingService {

    private final OrderStatusContract orderStatusService;

    Logger logger = LoggerFactory.getLogger(PaymentsProcessingService.class);

    public Boolean processPaymentSuccessStatus(Long orderId) {
        return saveStatus(new OrderStatusDto(
                READY,
                "Payment service",
                "Payment received " + LocalDateTime.now(),
                orderId
        ));
    }

    public Boolean processAwaitPaymentStatus(Long orderId) {
        return saveStatus(new OrderStatusDto(
                AWAIT_PAYMENT,
                "Payment service",
                "Payment received " + LocalDateTime.now(),
                orderId
        ));
    }

    private boolean saveStatus(OrderStatusDto orderStatusDto) {
        try {
            orderStatusService.create(orderStatusDto);
            return true;
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            return false;
        }
    }
}
