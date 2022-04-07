package com.rjtech.finance.req;

import com.rjtech.common.dto.ClientTO;

public class ProfitCentreGetReq extends ClientTO {

    private static final long serialVersionUID = 5207019904694499427L;
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
