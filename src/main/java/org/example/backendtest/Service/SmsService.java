package org.example.backendtest.Service;

public class SmsService implements SmsNotificationService {
    @Override
    public void sendSms(String phoneNumber, String message) {

        System.out.println("SMS sent to " + phoneNumber + ": " + message);
    }
}
