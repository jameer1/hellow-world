package com.rjtech.common.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProvisionTO;
import com.rjtech.common.resp.AppResp;

public class ProvisionSaveReq extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProvisionTO> provisionTOs = null;

    public ProvisionSaveReq() {
        provisionTOs = new ArrayList<ProvisionTO>();
    }

    public List<ProvisionTO> getProvisionTOs() {
        return provisionTOs;
    }

    public void setProvisionTOs(List<ProvisionTO> provisionTOs) {
        this.provisionTOs = provisionTOs;
    }

}