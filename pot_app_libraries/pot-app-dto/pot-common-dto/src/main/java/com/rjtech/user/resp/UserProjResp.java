package com.rjtech.user.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;

import com.rjtech.user.dto.UserProjDetailsTO;

public class UserProjResp extends AppResp {
    private static final long serialVersionUID = 2950084862079755848L;

    private List<UserProjDetailsTO> userProjDetailsTOs = null;

    public UserProjResp() {
        userProjDetailsTOs = new ArrayList<UserProjDetailsTO>(5);
    }

    public List<UserProjDetailsTO> getUserProjDetailsTOs() {
        return userProjDetailsTOs;
    }

}
