package ru.kuznetsov.shop.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import ru.kuznetsov.shop.business.access.config.DataAccessConfig;
import ru.kuznetsov.shop.kafka.config.KafkaConfig;

@SpringBootApplication
@EnableDiscoveryClient
@Import({DataAccessConfig.class, KafkaConfig.class})
public class PaymentModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentModuleApplication.class, args);
    }

}
