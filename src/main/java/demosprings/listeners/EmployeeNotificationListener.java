
package demosprings.listeners;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import demosprings.events.EmployeeCreatedEvent;
import demosprings.service.EmailService;
import demosprings.service.SmsService;

@Component
public class EmployeeNotificationListener {

    private final EmailService emailService;
    private final SmsService smsService;

    public EmployeeNotificationListener(EmailService emailService, SmsService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserCreated(EmployeeCreatedEvent event) {
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
        if (u.getMobileno() != null) {
            smsService.sendSms(
                "+91" + u.getMobileno(),
                "Hi " + u.getFirstname() + ", welcome!", null
            );
        }
    }
}

