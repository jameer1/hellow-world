package com.rjtech.common.service.handler;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.user.dto.UserTO;

public class UserHandler {

    public static UserTO convertEntityToPOJO(UserMstrEntity userMstrEntity) {

        UserTO userTO = new UserTO();
        userTO.setUserId(userMstrEntity.getUserId());

        userTO.setUserName(userMstrEntity.getUserName());
        userTO.setEmail(userMstrEntity.getEmail());

        userTO.setStatus(userMstrEntity.getStatus());

        return userTO;

    }

    public static UserMstrEntity convertPOJOToEntity(UserTO userTO) {

        UserMstrEntity userMstrEntity = new UserMstrEntity();
        if (CommonUtil.isNonBlankLong(userTO.getUserId())) {
            userMstrEntity.setUserId(userTO.getUserId());
        }
        userMstrEntity.setUserName(userTO.getUserName());
        userMstrEntity.setEmail(userTO.getEmail());

        userMstrEntity.setStatus(userTO.getStatus());

        return userMstrEntity;
    }

}
