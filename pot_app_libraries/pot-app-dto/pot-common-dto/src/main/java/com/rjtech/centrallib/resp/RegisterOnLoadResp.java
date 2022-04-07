package com.rjtech.centrallib.resp;

import com.rjtech.centrallib.dto.RegisterOnLoadTO;
import com.rjtech.common.resp.AppResp;

public class RegisterOnLoadResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RegisterOnLoadResp() {

    }

    private RegisterOnLoadTO registerOnLoadTO = new RegisterOnLoadTO();

    public RegisterOnLoadTO getRegisterOnLoadTO() {
        return registerOnLoadTO;
    }

    public void setRegisterOnLoadTO(RegisterOnLoadTO registerOnLoadTO) {
        this.registerOnLoadTO = registerOnLoadTO;
    }

}
