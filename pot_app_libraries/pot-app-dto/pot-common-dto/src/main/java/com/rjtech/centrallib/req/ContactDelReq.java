package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;



public class ContactDelReq extends CompanyGetReq {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Long> contactIds = new ArrayList<Long>(5);

    public List<Long> getContactIds() {
        return contactIds;
    }

    public void setContactIds(List<Long> contactIds) {
        this.contactIds = contactIds;
    }

}
