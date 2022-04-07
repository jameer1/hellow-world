package com.rjtech.user.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.user.dto.EmailSettingTO;

public class EmailSettingResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 2740448397989044791L;

    private List<EmailSettingTO> emailSettingTOs = null;

    public EmailSettingResp() {
        emailSettingTOs = new ArrayList<EmailSettingTO>();
    }

    public List<EmailSettingTO> getEmailSettingTOs() {
        return emailSettingTOs;
    }

}
