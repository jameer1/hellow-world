package com.rjtech.centrallib.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.AddressTO;
import com.rjtech.centrallib.dto.ContactsTO;
import com.rjtech.centrallib.dto.ProcurementCompanyTO;
import com.rjtech.common.resp.AppResp;


public class ProcurementCompanyResp extends AppResp {
    private static final long serialVersionUID = 2950084862079755L;

    private List<ProcurementCompanyTO> companyTOs = null;
    private Map<Long, ProcurementCompanyTO> companyMap = null;

    Map<Long, ContactsTO> contactsMap = null;
    Map<Long, AddressTO> addressMap = null;

    public ProcurementCompanyResp() {
        companyTOs = new ArrayList<ProcurementCompanyTO>(5);
        companyMap = new HashMap<Long, ProcurementCompanyTO>();
        addressMap = new HashMap<Long, AddressTO>();
        contactsMap = new HashMap<Long, ContactsTO>();
    }

    public List<ProcurementCompanyTO> getCompanyTOs() {
        return companyTOs;
    }

    public void setCompanyTOs(List<ProcurementCompanyTO> companyTOs) {
        this.companyTOs = companyTOs;
    }

    public Map<Long, ContactsTO> getContactsMap() {
        return contactsMap;
    }

    public void setContactsMap(Map<Long, ContactsTO> contactsMap) {
        this.contactsMap = contactsMap;
    }

    public Map<Long, AddressTO> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<Long, AddressTO> addressMap) {
        this.addressMap = addressMap;
    }

    public Map<Long, ProcurementCompanyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, ProcurementCompanyTO> companyMap) {
        this.companyMap = companyMap;
    }

}
