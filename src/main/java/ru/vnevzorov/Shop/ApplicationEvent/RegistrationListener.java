package ru.vnevzorov.Shop.ApplicationEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.vnevzorov.Shop.model.user.AbstractUser;
import ru.vnevzorov.Shop.service.email.EmailService;
import ru.vnevzorov.Shop.service.email.Message;
import ru.vnevzorov.Shop.service.user.AbstractUserService;

import java.util.Date;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private AbstractUserService abstractUserService;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        AbstractUser abstractUser = event.getAbstractUser();
        String token = UUID.randomUUID().toString();
        abstractUserService.createVerificationToken(abstractUser, token); //TODO нет expiryDate

        String recipientAddress = abstractUser.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.confirmRegistration", null, event.getLocale());

        Message confirmationMessage = new Message(recipientAddress, subject, message + "<br/><a href=\"http://localhost:8080" + confirmationUrl + "\">Confirm registration</a>");
        emailService.sendHtmlMessage(confirmationMessage);
    }
}
