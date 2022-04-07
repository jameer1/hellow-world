package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;


public class UserModulePermissionResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<LabelKeyTO> users = new ArrayList<LabelKeyTO>(5);

    public List<LabelKeyTO> getUsers() {
        return users;
    }

    public void setUsers(List<LabelKeyTO> users) {
        this.users = users;
    }

}