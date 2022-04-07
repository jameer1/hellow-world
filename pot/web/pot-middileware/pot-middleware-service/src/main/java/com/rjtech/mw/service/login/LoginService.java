package com.rjtech.mw.service.login;

import com.rjtech.mw.dto.login.LoginRequest;
import com.rjtech.mw.dto.login.LoginResponse;

public interface LoginService {

    LoginResponse validateExternalLogin(LoginRequest loginRequest);

    LoginResponse validateInternalLogin(LoginRequest loginRequest);

}
