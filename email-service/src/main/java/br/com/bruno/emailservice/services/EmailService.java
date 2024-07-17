package br.com.bruno.emailservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.text-created-order-confirmation}")
    private String textCreatedOrderConfirmation;

    public void sendMail(final OrderCreatedMessage orderCreatedMessage) {
        log.info("Sending email to: {}", orderCreatedMessage.getCustomer().email());

        SimpleMailMessage mailMessage = getSimpleMailMessage(orderCreatedMessage);

        try {
            mailSender.send(mailMessage);
            log.info("Email sent successfully to: {}", orderCreatedMessage.getCustomer().email());
        } catch (MailException e) {
            switch (e.getClass().getSimpleName()) {
                case "MailAuthenticationException":
                    log.error("Error sending email: Authentication failed");
                    break;
                case "MailSendException":
                    log.error("Error sending email: Connection refused\n: {}", e.getMessage());
                    break;
                default:
                    log.error("Error sending email: {}", e.getMessage());
                    break;
            }
        }
    }

    private SimpleMailMessage getSimpleMailMessage(OrderCreatedMessage orderCreatedMessage) {
        String subject = "Order created";
        String text = String.format(textCreatedOrderConfirmation,
                orderCreatedMessage.getCustomer().name(),
                orderCreatedMessage.getOrder().id(),
                orderCreatedMessage.getOrder().title(),
                orderCreatedMessage.getOrder().description(),
                orderCreatedMessage.getOrder().createdAt(),
                orderCreatedMessage.getOrder().status(),
                orderCreatedMessage.getRequester().name());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(orderCreatedMessage.getCustomer().email());
        mailMessage.setText(text);

        return mailMessage;
    }
}
