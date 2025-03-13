package org.example.backendtest.Service;


import lombok.extern.slf4j.Slf4j;
import org.example.backendtest.DTO.MpesaCallbackResponse;
import org.example.backendtest.Entity.TransactionEntity ;
import org.example.backendtest.Repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MpesaTransactionService {

    private final TransactionRepository transactionRepository;

    public MpesaTransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void processTransactionCallback(MpesaCallbackResponse callbackResponse) {
        MpesaCallbackResponse.ResultData result = callbackResponse.getResult();

        log.info("Processing transaction callback: {}", result);

        if (result.getResultCode() == 0) {
            log.info("Transaction successful: {}", result.getTransactionID());

            TransactionEntity transaction = new TransactionEntity();
            transaction.setTransactionId(result.getTransactionID());
            transaction.setAmount(result.getTransactionAmount());
            transaction.setStatus("SUCCESS");
            transaction.setDescription(result.getResultDesc());

            transactionRepository.save(transaction);
        } else {
            log.warn("Transaction failed: {}", result.getResultDesc());
        }
    }

}

