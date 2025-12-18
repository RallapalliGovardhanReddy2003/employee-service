package com.example.employeeservice.controller;

import org.springframework.web.bind.annotation.*;

import com.example.employeeservice.service.SmsService;

@RestController
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public SmsResponse sendSms(@RequestBody SmsRequest req) {
        String sid = smsService.sendSms(req.getFrom(), req.getTo(), req.getBody());
        return new SmsResponse("queued", sid);
    }

    public static class SmsRequest {
        private String from;
        private String to;
        private String body;
        // getters//setters
        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
    }

    public static class SmsResponse {
        private String status;
        private String sid;
        public SmsResponse(String status, String sid) { this.status = status; this.sid = sid; }
        public String getStatus() { return status; }
        public String getSid() { return sid; }
    }
}
