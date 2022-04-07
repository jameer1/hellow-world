package com.rjtech.common.service.handler;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.EmailSettingEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.user.dto.EmailSettingTO;

public class EmailSettingHandler {

    public static EmailSettingTO converEntityToPOJO(EmailSettingEntity emailSettingEntity) {
        EmailSettingTO emailSettingTO = new EmailSettingTO();
        emailSettingTO.setId(emailSettingEntity.getId());

        emailSettingTO.setHost(emailSettingEntity.getHost());
        emailSettingTO.setPort(emailSettingEntity.getPort());

        emailSettingTO.setFromEmail(emailSettingEntity.getFromEmail());
        emailSettingTO.setUserName(emailSettingEntity.getUserName());
        emailSettingTO.setPassword(emailSettingEntity.getPassword());

        ClientRegEntity clientId = emailSettingEntity.getClientId();
        if (CommonUtil.objectNotNull(clientId)) {
            emailSettingTO.setClientId(clientId.getClientId());
        }

        emailSettingTO.setStatus(emailSettingEntity.getStatus());

        return emailSettingTO;
    }

    public static EmailSettingEntity converPOJOToEntity(EmailSettingTO emailSettingTO) {
        EmailSettingEntity emailSettingEntity = new EmailSettingEntity();
        if (CommonUtil.objectNotNull(emailSettingTO.getId())) {
            emailSettingEntity.setId(emailSettingTO.getId());
        }
        emailSettingEntity.setHost(emailSettingTO.getHost());
        emailSettingEntity.setPort(emailSettingTO.getPort());

        emailSettingEntity.setFromEmail(emailSettingTO.getFromEmail());
        emailSettingEntity.setUserName(emailSettingTO.getUserName());
        emailSettingEntity.setPassword(emailSettingTO.getPassword());

        emailSettingEntity.setStatus(emailSettingTO.getStatus());

        return emailSettingEntity;
    }

}
