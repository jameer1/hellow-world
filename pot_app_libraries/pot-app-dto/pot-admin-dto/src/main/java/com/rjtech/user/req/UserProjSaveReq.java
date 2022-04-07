package com.rjtech.user.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.user.dto.UserProjDetailsTO;

public class UserProjSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private List<UserProjDetailsTO> userProjectTOs = new ArrayList<UserProjDetailsTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<UserProjDetailsTO> getUserProjectTOs() {
        return userProjectTOs;
    }

    public void setUserProjectTOs(List<UserProjDetailsTO> userProjectTOs) {
        this.userProjectTOs = userProjectTOs;
    }

}
