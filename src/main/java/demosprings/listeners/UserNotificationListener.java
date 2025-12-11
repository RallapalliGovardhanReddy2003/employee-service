
package demosprings.listeners;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import demosprings.events.UserCreatedEvent;
import demosprings.service.EmailService;
import demosprings.service.SmsService;

@Component
public class UserNotificationListener {

    private final EmailService emailService;
    private final SmsService smsService;

    public UserNotificationListener(EmailService emailService, SmsService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserCreated(UserCreatedEvent event) {
        var u = event.getEmployee();

        // Email
        if (u.getEmailid() != null) {
            emailService.sendSimple(
                u.getEmailid(),
                "Welcome " + u.getFirstname(),
                "Hello " + u.getFirstname() + ", your registration is successful!"
            );
        }

        // SMS
        if (u.getMonbno() != null) {
            smsService.sendSms(
                "+91" + u.getMonbno(),
                "Hi " + u.getFirstname() + ", welcome!", null
            );
        }
    }
}

