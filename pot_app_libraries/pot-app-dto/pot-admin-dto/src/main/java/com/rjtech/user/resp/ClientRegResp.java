package com.rjtech.user.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.user.dto.ClientRegTO;

public class ClientRegResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 2740448397989044791L;

    List<ClientRegTO> clientRegTOs = new ArrayList<ClientRegTO>();;

    public ClientRegResp() {
    }

    public List<ClientRegTO> getClientRegTOs() {
        return clientRegTOs;
    }

}
