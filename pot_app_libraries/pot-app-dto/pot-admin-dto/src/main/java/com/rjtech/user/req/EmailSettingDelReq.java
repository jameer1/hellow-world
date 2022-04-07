package com.rjtech.user.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;

public class EmailSettingDelReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private List<Long> emailSettingIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getEmailSettingIds() {
        return emailSettingIds;
    }

    public void setEmailSettingIds(List<Long> emailSettingIds) {
        this.emailSettingIds = emailSettingIds;
    }

}
