package com.rjtech.mw.service.impl.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.common.utils.UserTypes;
import com.rjtech.login.repository.LoginRepositoryCopy;
import com.rjtech.mw.dto.login.LoginRequest;
import com.rjtech.mw.dto.login.LoginResponse;
import com.rjtech.mw.service.login.LoginService;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private LoginRepositoryCopy loginRepository;

    public LoginResponse validateExternalLogin(LoginRequest loginRequest) {
        LoginResponse resp = null;
        UserMstrEntity usrEntity = loginRepository.findExternalUsers(loginRequest.getClientCode(),
                loginRequest.getUsername(), StatusCodes.ACTIVE.getValue(), UserTypes.EXTERNAL.getValue());
        if (CommonUtil.objectNotNull(usrEntity)) {
            if (usrEntity.getPassword().equals(loginRequest.getPassword())) {
                resp = new LoginResponse();
                resp.setClientId(usrEntity.getClientRegEntity().getClientId());
                resp.setClientCode(usrEntity.getClientRegEntity().getCode());
                resp.setUserId(usrEntity.getUserId());
                resp.setUserType(usrEntity.getUserType());
                resp.setLicence(usrEntity.getClientRegEntity().getLicence());
                resp.setDesignation(usrEntity.getEmpDesg());
            }
        }
        return resp;
    }

    public LoginResponse validateInternalLogin(LoginRequest loginRequest) {
        LoginResponse resp = null;
        UserMstrEntity usrEntity = loginRepository.findInternalUsers(loginRequest.getUsername(),
                StatusCodes.ACTIVE.getValue(), UserTypes.INTERNAL.getValue());
        if (CommonUtil.objectNotNull(usrEntity)) {
            if (usrEntity.getPassword().equals(loginRequest.getPassword())) {
                resp = new LoginResponse();
                resp.setUserId(usrEntity.getUserId());
                log.info("User Entity ***************  {}", usrEntity.getUserName());
                resp.setUserType(usrEntity.getUserType());
                resp.setAdminClientId(usrEntity.getClientRegEntity().getClientId());
            }
        }
        return resp;
    }

}
