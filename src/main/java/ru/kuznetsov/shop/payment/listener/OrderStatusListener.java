package ru.kuznetsov.shop.payment.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.kuznetsov.shop.represent.contract.order.OrderContract;
import ru.kuznetsov.shop.represent.contract.order.PaymentContract;
import ru.kuznetsov.shop.represent.dto.order.OrderDto;
import ru.kuznetsov.shop.represent.dto.order.UpdateOrderDTO;
import ru.kuznetsov.shop.represent.dto.payment.ReceiptDto;

import java.time.LocalDateTime;

import static ru.kuznetsov.shop.represent.common.KafkaConst.ORDER_STATUS_FORMED_TOPIC;

@Component
@RequiredArgsConstructor
public class OrderStatusListener {

    @Value("${receipt.expiration-minutes}")
    private int EXPIRATION_TIME_MINUTES = 360;

    private final ObjectMapper objectMapper;
    private final OrderContract orderService;
    private final PaymentContract paymentService;

    @KafkaListener(topics = ORDER_STATUS_FORMED_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
    public void processFormedStatus(String updateStatusDto) {
        try {
            UpdateOrderDTO dto = objectMapper.readValue(updateStatusDto, UpdateOrderDTO.class);

            OrderDto order = orderService.getById(dto.getOrderId());

            paymentService.sendReceipt(
                    new ReceiptDto(
                            order.getId(),
                            order.getTotalSum(),
                            "Process payment",
                            order.getBucket(),
                            LocalDateTime.now(),
                            LocalDateTime.now().plusMinutes(EXPIRATION_TIME_MINUTES)
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
