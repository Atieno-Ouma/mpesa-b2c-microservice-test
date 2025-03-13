package org.example.backendtest.DTO;
import lombok.Data;

@Data
public class MpesaCallbackResponse {
    private String transactionReference;
    private ResultData Result;
    private boolean success;
    private String message;

    @Data
    public static class ResultData {
        private int ResultType;
        private int ResultCode;
        private String ResultDesc;
        private String OriginatorConversationID;
        private String ConversationID;
        private String TransactionID;
        private double TransactionAmount;
        private String ReceiverPartyPublicName;
        private String TransactionCompletedDateTime;
    }
}


