package org.example.backendtest.Controller;
import org.example.backendtest.DTO.MpesaB2CTransactionRequest;
import org.example.backendtest.DTO.MpesaCallbackResponse;
import org.example.backendtest.Service.MpesaTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.backendtest.Service.MpesaB2CService;
import org.example.backendtest.Service.SmsNotificationService;

@RestController
@RequestMapping("/api/mpesa/b2c")
@N

public class MpesaB2CTransactionController {
    private final MpesaB2CService mpesab2cService;
    private final SmsNotificationService smsNotificationService;
    private final MpesaTransactionService mpesaTransactionService;

    public MpesaB2CTransactionController(MpesaB2CService mpesab2cService, SmsNotificationService smsNotificationService, MpesaTransactionService mpesaTransactionService) {
        this.mpesab2cService = mpesab2cService;
        this.smsNotificationService = smsNotificationService;
        this.mpesaTransactionService = mpesaTransactionService;
    }

    @PostMapping("/pay")
    public MpesaCallbackResponse initiateB2CTransaction(@RequestBody MpesaB2CTransactionRequest request) {
        MpesaCallbackResponse response = mpesab2cService.processB2CTransaction(request);

        String smsMessage = response.isSuccess()
                ? "Your payment of KES " + request.getAmount() + " was successful. Ref: " + response.getTransactionReference()
                : "Your payment of KES " + request.getAmount() + " failed. Please try again.";

        smsNotificationService.sendSms(request.getPhoneNumber(), smsMessage);

        return response;
    }

    @PostMapping("/transaction-result")
    public ResponseEntity<String> handleTransactionCallback(@RequestBody MpesaCallbackResponse callbackResponse) {
        log.info("Received Mpesa transaction callback: {}", callbackResponse);

        mpesaTransactionService.processTransactionCallback(callbackResponse);

        return ResponseEntity.ok("Callback received successfully");
    }
}

