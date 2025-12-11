package demosprings.controller;

import demosprings.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public EmailResponse sendEmail(@RequestBody EmailRequest req) {
        emailService.sendSimple(req.getTo(), req.getSubject(), req.getText());
        return new EmailResponse("Email sent successfully!");
    }

    // Request Body Class
    public static class EmailRequest {
        private String to;
        private String subject;
        private String text;

        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
    }

    // Response Class
    public static class EmailResponse {
        private String message;

        public EmailResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
