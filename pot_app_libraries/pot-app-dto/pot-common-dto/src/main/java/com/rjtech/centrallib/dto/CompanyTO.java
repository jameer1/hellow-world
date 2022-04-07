package com.rjtech.centrallib.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;

public class CompanyTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;
    private Long id;
    private String cmpName;
    private String cmpCode;
    private String regNo;
    private String taxFileNo;
    private boolean select;
    private String cmpActivity;

    private String businessCategory;
    private String companyCatagory;
    private boolean projectCompany;

    private List<AddressTO> addressList = new ArrayList<AddressTO>();
    private List<ContactsTO> contacts = new ArrayList<ContactsTO>();
    private List<CompanyProjectsTO> currentProjs = new ArrayList<CompanyProjectsTO>();
    private List<CompanyProjectsTO> closedProjs = new ArrayList<CompanyProjectsTO>();
    private List<BankTO> bankList = new ArrayList<BankTO>();

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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getCompanyCatagory() {
        return companyCatagory;
    }

    public void setCompanyCatagory(String companyCatagory) {
        this.companyCatagory = companyCatagory;
    }

    public List<AddressTO> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressTO> addressList) {
        this.addressList = addressList;
    }

    public List<ContactsTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactsTO> contacts) {
        this.contacts = contacts;
    }

    public List<CompanyProjectsTO> getCurrentProjs() {
        return currentProjs;
    }

    public void setCurrentProjs(List<CompanyProjectsTO> currentProjs) {
        this.currentProjs = currentProjs;
    }

    public List<CompanyProjectsTO> getClosedProjs() {
        return closedProjs;
    }

    public void setClosedProjs(List<CompanyProjectsTO> closedProjs) {
        this.closedProjs = closedProjs;
    }

    public String getCmpActivity() {
        return cmpActivity;
    }

    public void setCmpActivity(String cmpActivity) {
        this.cmpActivity = cmpActivity;
    }
    
    public boolean isProjectCompany() {
        return projectCompany;
    }

    public void setProjectCompany(boolean projectCompany) {
        this.projectCompany = projectCompany;
    }
    
    public List<BankTO> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankTO> bankList) {
        this.bankList = bankList;
    }

}