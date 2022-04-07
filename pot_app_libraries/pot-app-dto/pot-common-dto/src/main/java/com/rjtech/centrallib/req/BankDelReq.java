package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;



public class BankDelReq extends CompanyGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> bankAccountIds = new ArrayList<Long>(5);
    
    public List<Long> getBankAccountIds() {
        return bankAccountIds;
    }

    public void setBankAccountIds(List<Long> bankAccountIds) {
        this.bankAccountIds = bankAccountIds;
    }
    
    public String toString() {
    	return "bankIds : "+bankAccountIds;
    }

}
