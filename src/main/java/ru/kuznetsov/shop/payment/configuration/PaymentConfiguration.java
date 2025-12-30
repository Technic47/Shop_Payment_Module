package ru.kuznetsov.shop.payment.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kuznetsov.shop.kafka.service.MessageCacheService;
import ru.kuznetsov.shop.payment.service.PaymentContractMock;
import ru.kuznetsov.shop.represent.contract.order.PaymentContract;

@Configuration
public class PaymentConfiguration {

    @Bean
    @ConditionalOnMissingBean(PaymentContract.class)
    public PaymentContract paymentService() {
        return new PaymentContractMock();
    }

    @Bean
    public MessageCacheService<Long> getMessageCacheService(){
        return new MessageCacheService<>();
    }
}
