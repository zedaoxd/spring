package br.com.bruno.emailservice.services;

import br.com.bruno.emailservice.models.enums.OperationEnum;
import br.com.bruno.emailservice.utils.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendHtmlMail(final OrderCreatedMessage orderCreatedMessage, OperationEnum operation) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        String process = getContext(orderCreatedMessage, operation);

        EmailUtils.getMimeMessage(message, process, orderCreatedMessage, operation.getOperation());

        mailSender.send(message);
    }

    private String getContext(OrderCreatedMessage orderDTO, OperationEnum operation) {
        Context context = new Context();

        return switch (operation) {
            case ORDER_CREATED -> {
                log.info("Enviando email de criação de ordem de serviço");
                context = EmailUtils.getContextToCreatedOrder(orderDTO);
                yield templateEngine.process("email/order-created", context);
            }
            case ORDER_UPDATED -> {
                log.info("Enviando email de atualização de ordem de serviço");
                // context = EmailUtils.getContextToUpdatedOrder(orderDTO);
                yield templateEngine.process("email/order-updated", context);
            }
            case ORDER_DELETED -> {
                log.info("Enviando email de exclusão de ordem de serviço");
                // context = EmailUtils.getContextToDeletedOrder(orderDTO);
                yield templateEngine.process("email/order-deleted", context);
            }
        };
    }
}
