package ru.vnevzorov.Shop.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMessage(Message message) {
        if (message.getPathToAttachment() == null) {
            sendSimpleMessage(message);
        } else {
            sendMessageWithAttachment(message);
        }
    }

    private void sendSimpleMessage(Message message) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(message.getTo());
        simpleMessage.setSubject(message.getSubject());
        simpleMessage.setText(message.getText());

        javaMailSender.send(simpleMessage);
    }

    private void sendMessageWithAttachment(Message message) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getText());

            FileSystemResource file
                    = new FileSystemResource(new File(message.getPathToAttachment()));
            helper.addAttachment(message.getPathToAttachment(), file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }
}
