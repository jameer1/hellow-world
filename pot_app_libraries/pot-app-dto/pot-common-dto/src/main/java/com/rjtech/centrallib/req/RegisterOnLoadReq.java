package com.rjtech.centrallib.req;

import java.io.Serializable;

public class RegisterOnLoadReq implements Serializable {

    private static final long serialVersionUID = 1965934691087769709L;

    public RegisterOnLoadReq() {

    }

    private String procureCatg;
    private String procureCategoryDbConstant;

    public String getProcureCatg() {
        return procureCatg;
    }

    public void setProcureCatg(String procureCatg) {
        this.procureCatg = procureCatg;
    }

    public String getProcureCategoryDbConstant() {
        return procureCategoryDbConstant;
    }

    public void setProcureCategoryDbConstant(String procureCategoryDbConstant) {
        this.procureCategoryDbConstant = procureCategoryDbConstant;
    }

}
