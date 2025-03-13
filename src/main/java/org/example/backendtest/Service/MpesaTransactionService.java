package org.example.backendtest.Service;


import lombok.extern.slf4j.Slf4j;
import org.example.backendtest.DTO.MpesaCallbackResponse;
import org.example.backendtest.Entity.TransactionEntity ;
import org.example.backendtest.Repository.TransactionRepository;
import org.example.backendtest.Service.SmsNotificationService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MpesaTransactionService {

    private final TransactionRepository transactionRepository;

    public MpesaTransactionService(TransactionRepository transactionRepository, SmsNotificationService smsNotificationService) {
        this.transactionRepository = transactionRepository;
        this.smsNotificationService = smsNotificationService;
    }

    public void processTransactionCallback(MpesaCallbackResponse callbackResponse) {
        MpesaCallbackResponse.ResultData result = callbackResponse.getResult();

        log.info("Processing transaction callback: {}", result);

        String message;

        if (result.getResultCode() == 0) {
            log.info("Transaction successful: {}", result.getTransactionID());

            TransactionEntity transaction = new TransactionEntity();
            transaction.setTransactionId(result.getTransactionID());
            transaction.setAmount(result.getTransactionAmount());
            transaction.setStatus("SUCCESS");
            transaction.setDescription(result.getResultDesc());
            transactionRepository.save(transaction);

            message = "Dear customer, your payment of KES " + result.getTransactionAmount() +
                    " was successful. Ref: " + result.getTransactionID();

        } else {
            log.warn("Transaction failed: {}", result.getResultDesc());

            message = "Dear customer, your payment failed. Reason: " + result.getResultDesc();
        }

        smsNotificationService.sendSms(result.getReceiverPartyPublicName(), message);
    }

}

