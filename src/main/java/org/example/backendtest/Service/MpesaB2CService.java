package org.example.backendtest.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.backendtest.DTO.MpesaB2CTransactionRequest;
import org.example.backendtest.DTO.MpesaCallbackResponse;
import org.example.backendtest.Service.SmsNotificationService;

import org.springframework.stereotype.Service;



@Service
@Slf4j
public interface MpesaB2CService {

    public MpesaB2CTransactionService(SmsNotificationService smsNotificationService) {
        this.smsNotificationService = smsNotificationService;
    }

    public default MpesaCallbackResponse processB2CTransaction(MpesaB2CTransactionRequest request) {
        log.info("Processing B2C transaction for phone: {}", request.getPhoneNumber());

        MpesaCallbackResponse response = new MpesaCallbackResponse();
        response.setTransactionReference("TXN123456789");
        response.setSuccess(true);

        String smsMessage;
        if (response.isSuccess()) {
            smsMessage = "Dear customer, your payment of KES " + request.getAmount() +
                    " was successful. Ref: " + response.getTransactionReference();
        } else {
            smsMessage = "Dear customer, your payment of KES " + request.getAmount() +
                    " failed. Please try again.";
        }

        smsNotificationService.sendSms(request.getPhoneNumber(), smsMessage);
    }
}
