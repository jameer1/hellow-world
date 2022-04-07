package com.rjtech.user.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.user.dto.EmailSettingTO;

public class EmailSettingSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private List<EmailSettingTO> emailSettingTOs = new ArrayList<EmailSettingTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<EmailSettingTO> getEmailSettingTOs() {
        return emailSettingTOs;
    }

    public void setEmailSettingTOs(List<EmailSettingTO> emailSettingTOs) {
        this.emailSettingTOs = emailSettingTOs;
    }

}
