package com.rjtech.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.rjtech.common.model.EmailSettingEntity;
import com.rjtech.common.repository.EmailSettingRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

@Service("emailServices")
public class CommonEmailServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(CommonEmailServiceImpl.class);

    @Autowired
    private EmailSettingRepository emailSettingRepository;

    public void sendEmailNotification(String toEmail, String ccEmail, String toSubject, String body) {
        List<EmailSettingEntity> emailSettingEntities = new ArrayList<>();
        emailSettingEntities = emailSettingRepository.findEmailSettings(AppUserUtils.getClientId(), 1);
        for (EmailSettingEntity emailSettingTo : emailSettingEntities) {

            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(emailSettingTo.getHost());
            sender.setPort(emailSettingTo.getPort());
            sender.setUsername(emailSettingTo.getFromEmail());
            sender.setPassword(emailSettingTo.getPassword());
            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.starttls.enable", "true");
            javaMailProperties.put("mail.smtp.auth", "true");
            javaMailProperties.put("mail.transport.protocol", "smtp");
            javaMailProperties.put("mail.debug", "true");
            sender.setJavaMailProperties(javaMailProperties);
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            try {
                helper.setFrom(emailSettingTo.getFromEmail());
                helper.setTo(toEmail);
                if (CommonUtil.isNotBlankStr(ccEmail))
                    helper.setCc(ccEmail);
                helper.setSubject(toSubject);
                helper.setText(body, true);

                sender.send(message);

            } catch (MailException e) {
                log.error("Mail Exception", e);

            } catch (MessagingException e) {
                log.error("Messaging Exception", e);
            }

        }
    }
}
