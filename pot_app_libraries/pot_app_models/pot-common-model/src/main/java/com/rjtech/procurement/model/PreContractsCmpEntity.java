package com.rjtech.procurement.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.centrallib.model.CmpAddressEntity;
import com.rjtech.centrallib.model.CmpContactsEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.constants.ApplicationConstants;

/**
 * The persistent class for the pre_contracts_cmp database table.
 * 
 */
@Entity
@Table(name = "pre_contracts_cmp")
public class PreContractsCmpEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8659541915042401666L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCC_CMP_ID")
    private CompanyMstrEntity companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCC_CAM_ID")
    private CmpAddressEntity addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCC_CCM_ID")
    private CmpContactsEntity contactId;

    @Column(name = "PCC_CMP_STATUS")
    private Integer cmpStatus;

    @Column(name = "PCC_STATUS")
    private Integer status;

    @Column(name = "PCC_REF_CODE")
    private String rfqCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCC_QUOTE_DATE")
    private Date quotedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCC_ISSUE_DATE")
    private Date rfqIssueDate;

    @Column(name = "PCC_QUOTE_REF_CODE")
    private String quoteRefCode;

    @Column(name = "PCC_VENDOR_CODE")
    private String vendorCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PCC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCC_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "PCC_BIDDING_STATUS")
    private String biddingStatus;

    @ManyToOne
    @JoinColumn(name = "PCC_PRC_ID")
    private PreContractEntity preContractEntity = new PreContractEntity();

    @OneToMany(mappedBy = "preContractsCmpEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PreContractCmpDocEntity> preContractCmpDocEntities = new ArrayList<PreContractCmpDocEntity>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    @ManyToOne
    @JoinColumn(name = "PCC_CMP_ID", insertable = false, updatable = false)
    private CompanyMstrEntity companyMstrEntity;

    public CompanyMstrEntity getCompanyMstrEntity() {
        return companyMstrEntity;
    }

    public void setCompanyMstrEntity(CompanyMstrEntity companyMstrEntity) {
        this.companyMstrEntity = companyMstrEntity;
    }

    public PreContractsCmpEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCmpStatus() {
        return cmpStatus;
    }

    public void setCmpStatus(Integer cmpStatus) {
        this.cmpStatus = cmpStatus;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public PreContractEntity getPreContractEntity() {
        return preContractEntity;
    }

    public void setPreContractEntity(PreContractEntity preContractEntity) {
        this.preContractEntity = preContractEntity;
    }

    public List<PreContractCmpDocEntity> getPreContractCmpDocEntities() {
        return preContractCmpDocEntities;
    }

    public void setPreContractCmpDocEntities(List<PreContractCmpDocEntity> preContractCmpDocEntities) {
        this.preContractCmpDocEntities = preContractCmpDocEntities;
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

    public Date getQuotedDate() {
        return quotedDate;
    }

    public void setQuotedDate(Date quotedDate) {
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

    public Date getRfqIssueDate() {
        return rfqIssueDate;
    }

    public void setRfqIssueDate(Date rfqIssueDate) {
        this.rfqIssueDate = rfqIssueDate;
    }

    public CompanyMstrEntity getCompanyId() {
        return companyId;
    }

    public void setCompanyId(CompanyMstrEntity companyId) {
        this.companyId = companyId;
    }

    public CmpAddressEntity getAddressId() {
        return addressId;
    }

    public void setAddressId(CmpAddressEntity addressId) {
        this.addressId = addressId;
    }

    public CmpContactsEntity getContactId() {
        return contactId;
    }

    public void setContactId(CmpContactsEntity contactId) {
        this.contactId = contactId;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }


    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

}
