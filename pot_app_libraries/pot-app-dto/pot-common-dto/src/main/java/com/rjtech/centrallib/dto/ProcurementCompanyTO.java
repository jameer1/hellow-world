package com.rjtech.centrallib.dto;

import java.util.HashMap;
import java.util.Map;

import com.rjtech.common.dto.ClientTO;

public class ProcurementCompanyTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;
    private Long id;
    private String cmpName;
    private String cmpCode;
    private Long catgId;
    private Long busActId;
    private String regNo;
    private String taxFileNo;
    private String cmpActivity;
    private String businessCategory;
    private String companyCategory;
    Map<Long, ContactsTO> contactsMap = new HashMap<Long, ContactsTO>();
    Map<Long, AddressTO> addressMap = new HashMap<Long, AddressTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public Long getCatgId() {
        return catgId;
    }

    public void setCatgId(Long catgId) {
        this.catgId = catgId;
    }

    public Long getBusActId() {
        return busActId;
    }

    public void setBusActId(Long busActId) {
        this.busActId = busActId;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getTaxFileNo() {
        return taxFileNo;
    }

    public void setTaxFileNo(String taxFileNo) {
        this.taxFileNo = taxFileNo;
    }

    public String getCmpActivity() {
        return cmpActivity;
    }

    public void setCmpActivity(String cmpActivity) {
        this.cmpActivity = cmpActivity;
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

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

}