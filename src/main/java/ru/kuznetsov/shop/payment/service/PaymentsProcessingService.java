package ru.kuznetsov.shop.payment.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.kuznetsov.shop.kafka.service.KafkaService;
import ru.kuznetsov.shop.represent.contract.order.OrderStatusContract;
import ru.kuznetsov.shop.represent.dto.order.OrderStatusDto;

import java.time.LocalDateTime;
import java.util.Collections;

import static ru.kuznetsov.shop.represent.common.KafkaConst.ORDER_STATUS_PAYMENT_SUCCESS_TOPIC;
import static ru.kuznetsov.shop.represent.enums.OrderStatusType.AWAIT_PAYMENT;
import static ru.kuznetsov.shop.represent.enums.OrderStatusType.READY;

@Service
@RequiredArgsConstructor
public class PaymentsProcessingService {

    private final KafkaService kafkaService;
    private final OrderStatusContract orderStatusService;

    Logger logger = LoggerFactory.getLogger(PaymentsProcessingService.class);

    public OrderStatusDto processPaymentSuccessStatus(Long orderId) {
        OrderStatusDto orderStatusSaved = orderStatusService.create(new OrderStatusDto(
                READY,
                "Payment service",
                "Payment received " + LocalDateTime.now(),
                orderId
        ));

        kafkaService.sendMessageWithEntity(orderStatusSaved,
                ORDER_STATUS_PAYMENT_SUCCESS_TOPIC,
                Collections.emptyMap());

        logger.info("Payment sent successfully received for order: {}", orderId);
        return orderStatusSaved;
    }

    public OrderStatusDto processAwaitPaymentStatus(Long orderId) {
        OrderStatusDto paymentService = orderStatusService.create(new OrderStatusDto(
                AWAIT_PAYMENT,
                "Payment service",
                "Await payment " + LocalDateTime.now(),
                orderId
        ));

        logger.info("Order: {} is awaiting for payment", orderId);
        return paymentService;
    }
}
