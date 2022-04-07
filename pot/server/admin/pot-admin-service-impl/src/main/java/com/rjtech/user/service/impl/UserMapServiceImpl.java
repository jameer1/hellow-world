package com.rjtech.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.model.EmailSettingEntity;
import com.rjtech.common.repository.EmailSettingRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.user.model.ClientRegMstrEntity;
import com.rjtech.user.model.UserEntity;
import com.rjtech.user.repository.ClientRepository;
import com.rjtech.user.repository.UserRepository;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.resp.ClientMapResp;
import com.rjtech.user.resp.UserMapResp;
import com.rjtech.user.service.UserMapService;

@Service(value = "userMapService")
@RJSService(modulecode = "userMapService")
@Transactional
public class UserMapServiceImpl implements UserMapService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmailSettingRepository emailSettingRepository;

    public UserMapResp UserServiceMap(ClientReq clientReq) {

        UserMapResp userMapResp = new UserMapResp();
        List<UserEntity> userEntities = userRepository.findAllUsers((AppUserUtils.getClientId()),
                clientReq.getUserName(), clientReq.getEmpCode());
        String userName = null;
        String empCode = null;
        String code = null;
        if (CommonUtil.isListHasData(userEntities)) {
            for (UserEntity userEntity : userEntities) {

                userName = userEntity.getUserName();
                empCode = userEntity.getEmpCode();
                if (empCode != null) {
                    code = CommonUtil.concatCodeName(userName, empCode);
                } else {
                    code = userName;
                }

                if (CommonUtil.isNotBlankStr(code)) {
                    userMapResp.getUserUniqueMap().put(code.toUpperCase().trim(), userEntity.getStatus());

                }
            }
        }
        return userMapResp;

    }

    public ClientMapResp ClientServiceMap(ClientReq clientReq) {
        ClientMapResp clientMapResp = new ClientMapResp();
        List<ClientRegMstrEntity> clientRegMstrEntities = clientRepository.findAllClients();
        if (CommonUtil.isListHasData(clientRegMstrEntities)) {
            for (ClientRegMstrEntity clientRegMstrEntity : clientRegMstrEntities) {
                if (CommonUtil.isNotBlankStr(clientRegMstrEntity.getCode())) {
                    clientMapResp.getClientUniqueMap().put(clientRegMstrEntity.getCode().toUpperCase().trim(),
                            clientRegMstrEntity.getStatus());
                }
            }
        }
        return clientMapResp;

    }

    public ClientMapResp emailServiceMap(ClientReq clientReq) {
        ClientMapResp clientMapResp = new ClientMapResp();
        List<EmailSettingEntity> emailSettingEntities = emailSettingRepository
                .findEmailSettingsByClient(AppUserUtils.getClientId(), StatusCodes.ACTIVE.getValue());
        if (CommonUtil.isListHasData(emailSettingEntities)) {
            for (EmailSettingEntity emailSettingEntity : emailSettingEntities) {
                if (CommonUtil.isNotBlankStr(emailSettingEntity.getFromEmail())) {
                    clientMapResp.getEmailUniqueMap().put(emailSettingEntity.getFromEmail().toUpperCase().trim(),
                            emailSettingEntity.getStatus());
                }
            }
        }
        return clientMapResp;

    }

}
