package com.example.employeeservice.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.http.TwilioRestClient;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final TwilioRestClient twilioClient;

	private String defaultFrom;
    
    String fromNumber = defaultFrom;

    
    // Optionally put a default from number in properties and inject that
    public SmsService(TwilioRestClient twilioClient) {
        this.twilioClient = twilioClient;
    }

    public String sendSms(String from, String to, String body) {
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                body
        ).create(twilioClient);
        return message.getSid();
    }
}
