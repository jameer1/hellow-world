package com.rjtech.common.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProvisionTO;

public class ProvisionResp extends AppResp {

    private static final long serialVersionUID = -3763356942184775462L;

    private List<ProvisionTO> provisionTOs = new ArrayList<>();

    public List<ProvisionTO> getProvisionTOs() {
        return provisionTOs;
    }

}