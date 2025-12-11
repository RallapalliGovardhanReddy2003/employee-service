package demosprings.config;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    private final String accountSid;
    private final String authToken;

    public TwilioConfig(
            @Value("${twilio.account-sid:}") String accountSid,
            @Value("${twilio.auth-token:}") String authToken) {
        this.accountSid = accountSid;
        this.authToken = authToken;
    }

    @Bean
    public TwilioRestClient twilioRestClient() {
        if (accountSid == null || accountSid.isBlank() || authToken == null || authToken.isBlank()) {
            throw new IllegalStateException("Twilio credentials not set. Configure twilio.account-sid and twilio.auth-token.");
        }
        Twilio.init(accountSid, authToken);
        return Twilio.getRestClient();
    }
}
