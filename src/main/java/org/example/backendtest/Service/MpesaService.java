package org.example.backendtest.Service;

import org.example.backendtest.DTO.MpesaB2CTransactionRequest;
import org.example.backendtest.DTO.MpesaCallbackResponse;
import org.springframework.stereotype.Service;

@Service

public class MpesaService implements MpesaB2CService{
    @Override
    public MpesaCallbackResponse processB2CTransaction(MpesaB2CTransactionRequest request) {

        MpesaCallbackResponse response = new MpesaCallbackResponse();
        response.setSuccess(true);
        response.setMessage("M-Pesa transaction successful");
        response.setTransactionReference("MPESA12345XYZ");
        return response;
    }
}
