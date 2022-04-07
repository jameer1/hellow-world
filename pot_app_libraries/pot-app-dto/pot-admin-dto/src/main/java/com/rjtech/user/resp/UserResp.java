package com.rjtech.user.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.user.dto.UserTO;

public class UserResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = -6671175298681215590L;

    private List<UserTO> users = new ArrayList<UserTO>();;

    public UserResp() {
    }

    public List<UserTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserTO> users) {
        this.users = users;
    }

}
