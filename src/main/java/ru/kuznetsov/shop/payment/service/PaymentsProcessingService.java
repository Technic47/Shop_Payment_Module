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

    public OrderStatusDto processPaymentSuccessStatus(Long orderId) {
        OrderStatusDto orderStatusSaved = orderStatusService.create(new OrderStatusDto(
                READY,
                null,
                "Payment received " + LocalDateTime.now(),
                orderId
        ));

        logger.info("Payment successfully received for order: {}", orderId);
        return orderStatusSaved;
    }

    public OrderStatusDto processAwaitPaymentStatus(Long orderId) {
        OrderStatusDto orderStatusDto = orderStatusService.create(new OrderStatusDto(
                AWAIT_PAYMENT,
                null,
                "Await payment " + LocalDateTime.now(),
                orderId
        ));

        logger.info("Order: {} set status AWAIT_PAYMENT", orderId);
        return orderStatusDto;
    }
}
