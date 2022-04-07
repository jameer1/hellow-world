package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;



public class AddressDelReq extends CompanyGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> addressIds = new ArrayList<Long>(5);

    public List<Long> getAddressIds() {
        return addressIds;
    }

    public void setAddressIds(List<Long> addressIds) {
        this.addressIds = addressIds;
    }

}
