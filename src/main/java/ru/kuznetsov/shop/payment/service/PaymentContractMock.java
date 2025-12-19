package ru.kuznetsov.shop.payment.service;

import ru.kuznetsov.shop.represent.contract.order.PaymentContract;
import ru.kuznetsov.shop.represent.dto.payment.ReceiptDto;

import java.util.Random;

public class PaymentContractMock implements PaymentContract {
    @Override
    public Boolean sendReceipt(ReceiptDto receipt) {
        Random random = new Random();
        if (random.nextInt(100) > 3) {
            return true;
        } else throw new RuntimeException("Ошибка при отправке чека на оплату");
    }
}
