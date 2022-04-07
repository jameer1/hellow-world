package com.rjtech.centrallib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ContactsTO;


public class ContactsSaveReq extends CompanyGetReq {
    private static final long serialVersionUID = -3763356942184L;

    private List<ContactsTO> contactsTOs = new ArrayList<ContactsTO>(5);

    public List<ContactsTO> getContactsTOs() {
        return contactsTOs;
    }

    public void setContactsTOs(List<ContactsTO> contactsTOs) {
        this.contactsTOs = contactsTOs;
    }

}
