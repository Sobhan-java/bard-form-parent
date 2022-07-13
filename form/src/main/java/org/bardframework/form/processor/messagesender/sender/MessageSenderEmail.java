package org.bardframework.form.processor.messagesender.sender;

import org.apache.commons.lang3.StringUtils;
import org.bardframework.form.field.FieldTemplate;
import org.bardframework.form.processor.messagesender.creator.MessageCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class MessageSenderEmail implements MessageSender {
    protected static final Logger LOGGER = LoggerFactory.getLogger(MessageSenderEmail.class);

    private final InternetAddress senderEmail;
    private final Properties configs;
    private final MessageCreator subjectCreator;
    private final FieldTemplate<?> receiverEmailFieldTemplate;
    private final Authenticator authenticator;

    public MessageSenderEmail(String senderEmail, String senderUsername, String senderPassword, Properties configs, MessageCreator subjectCreator, FieldTemplate<?> receiverEmailFieldTemplate) throws AddressException {
        this.senderEmail = InternetAddress.parse(senderEmail)[0];
        this.configs = configs;
        this.subjectCreator = subjectCreator;
        this.receiverEmailFieldTemplate = receiverEmailFieldTemplate;
        this.authenticator = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderUsername, senderPassword);
            }
        };
    }

    @Override
    public void send(String message, Map<String, String> args, Locale locale) throws Exception {
        String receiverEmail = args.get(this.getReceiverEmailFieldTemplate().getName());
        if (StringUtils.isBlank(receiverEmail)) {
            LOGGER.warn("receiver email not exist for [{}], can't send email", args);
            throw new IllegalStateException("receiver email not exist in args");
        }
        Session session = Session.getInstance(configs, this.authenticator);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(senderEmail);
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
        mimeMessage.setSubject(subjectCreator.create(args, locale));
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(message, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        mimeMessage.setContent(multipart);
        Transport.send(mimeMessage);
    }

    public FieldTemplate<?> getReceiverEmailFieldTemplate() {
        return receiverEmailFieldTemplate;
    }
}