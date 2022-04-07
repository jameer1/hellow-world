package com.rjtech.user.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.user.dto.UserTO;

public class UserSaveReq extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    List<UserTO> userTOs = new ArrayList<UserTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<UserTO> getUserTOs() {
        return userTOs;
    }

    public void setUserTOs(List<UserTO> userTOs) {
        this.userTOs = userTOs;
    }

}
