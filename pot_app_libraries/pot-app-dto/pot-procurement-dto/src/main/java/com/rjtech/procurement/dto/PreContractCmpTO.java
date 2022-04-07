package com.rjtech.procurement.dto;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.common.dto.ProjectTO;

public class PreContractCmpTO extends ProjectTO {

    private static final long serialVersionUID = 1138540803701159526L;
    private Long id;
    private Integer cmpStatus;
    private Long companyId;
    private Long addressId;
    private Long contactId;
    private String biddingStatus;
    private String rfqCode;
    private String quotedDate;
    private String quoteRefCode;
    private String vendorCode;
    private String rfqIssueDate;

    private PreContractTO preContractTO = new PreContractTO();
    private List<PreContractCmpDocsTO> preContractCmpDocsTOs = new ArrayList<PreContractCmpDocsTO>();
    private CompanyTO companyTO;

    private List<PreContractCmpTO> preContractCmpTOs = new ArrayList<>();

    public CompanyTO getCompanyTO() {
        return companyTO;
    }

    public void setCompanyTO(CompanyTO companyTO) {
        this.companyTO = companyTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PreContractTO getPreContractTO() {
        return preContractTO;
    }

    public void setPreContractTO(PreContractTO preContractTO) {
        this.preContractTO = preContractTO;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Integer getCmpStatus() {
        return cmpStatus;
    }

    public void setCmpStatus(Integer cmpStatus) {
        this.cmpStatus = cmpStatus;
    }

    public List<PreContractCmpDocsTO> getPreContractCmpDocsTOs() {
        return preContractCmpDocsTOs;
    }

    public void setPreContractCmpDocsTOs(List<PreContractCmpDocsTO> preContractCmpDocsTOs) {
        this.preContractCmpDocsTOs = preContractCmpDocsTOs;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public String getRfqCode() {
        return rfqCode;
    }

    public void setRfqCode(String rfqCode) {
        this.rfqCode = rfqCode;
    }

    public String getQuotedDate() {
        return quotedDate;
    }

    public void setQuotedDate(String quotedDate) {
        this.quotedDate = quotedDate;
    }

    public String getQuoteRefCode() {
        return quoteRefCode;
    }

    public void setQuoteRefCode(String quoteRefCode) {
        this.quoteRefCode = quoteRefCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getRfqIssueDate() {
        return rfqIssueDate;
    }

    public void setRfqIssueDate(String rfqIssueDate) {
        this.rfqIssueDate = rfqIssueDate;
    }

    public List<PreContractCmpTO> getPreContractCmpTOs() {
        return preContractCmpTOs;
    }

    public void setPreContractCmpTOs(List<PreContractCmpTO> preContractCmpTOs) {
        this.preContractCmpTOs = preContractCmpTOs;
    }
}
