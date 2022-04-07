package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ContactsTO;
import com.rjtech.common.resp.AppResp;


public class ContactsResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<ContactsTO> contactsTOs = null;

    public ContactsResp() {
        contactsTOs = new ArrayList<ContactsTO>(5);
    }

    public List<ContactsTO> getContactsTOs() {
        return contactsTOs;
    }

    public void setContactsTOs(List<ContactsTO> contactsTOs) {
        this.contactsTOs = contactsTOs;
    }

}
