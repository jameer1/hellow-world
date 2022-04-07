package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.common.dto.CountryTO;
import com.rjtech.user.dto.UserTO;

public class UserSaveReq extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<UserTO> userTOs = new ArrayList<UserTO>();

    public List<UserTO> getUserTOs() {
        return userTOs;
    }

    public void setUserTOs(List<UserTO> userTOs) {
        this.userTOs = userTOs;
    }

}