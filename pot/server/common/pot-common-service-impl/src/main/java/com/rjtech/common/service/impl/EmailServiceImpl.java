package com.rjtech.common.service.impl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.rjtech.common.dto.EmailTO;
import com.rjtech.common.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    VelocityEngine velocityEngine;

    public void sendEmail(EmailTO emailTO) {

        MimeMessagePreparator preparator = getMessagePreparator(emailTO);
        try {
            mailSender.send(preparator);
            log.info("Email has been sent.");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private MimeMessagePreparator getMessagePreparator(final EmailTO emailTO) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setSubject(emailTO.getSubject());
                helper.setFrom(emailTO.getFromEmail());
                helper.setTo(emailTO.getToEmail());

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("emailTO", emailTO);

                String text = geVelocityTemplateContent(model);
                log.info("Template content : {}", text);
                helper.setText(text, true);
            }
        };
        return preparator;
    }

    public String geVelocityTemplateContent(Map<String, Object> model) {
        StringWriter content = new StringWriter();
        VelocityContext context = new VelocityContext();
        try {
            model.entrySet().forEach((entry) -> {
                context.put(entry.getKey(), entry.getValue());
            });
            EmailTO emailTO = (EmailTO) model.get("emailTO");
            velocityEngine.mergeTemplate(emailTO.getTemplateName(), "UTF-8", context, content);
            return content.toString();
        } catch (Exception e) {
            log.error("Exception occured while processing velocity template", e);
        }
        return "";
    }

}