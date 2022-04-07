package com.rjtech.procurement.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.eps.model.ProjMstrEntity;

@Entity
@Table(name = "pre_contract_transmittal_message")
public class DocumentTransmittalMessageEntity implements Serializable {

    private static final long serialVersionUID = -947281714613904712L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DTM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DTM_PRE_CONTRACT_ID")
    private PreContractEntity contractId;

    @Column(name = "DTM_DESC")
    private String desc;

    @Column(name = "DTM_ISSUED_BY")
    private String issuedBy;

    @Column(name = "DTM_ACCEPTED_BY")
    private String acceptedBy;

    @Column(name = "DTM_SIGN")
    private String sign;

    @Column(name = "DTM_NAME")
    private String name;

    @Column(name = "DTM_DESIGNATION")
    private String designation;

    @Column(name = "DTM_CMP_REP")
    private String companyRep;

    @Column(name = "DTM_VENDOR_REP")
    private String vendorRep;

    @Column(name = "DTM_VENDOR_NAME")
    private String vendorName;

    @Column(name = "DTM_VENDOR_SIGN")
    private String vendorSign;

    @Column(name = "DTM_VENDOR_DESIGNATION")
    private String vendorDesignation;

    @ManyToOne
    @JoinColumn(name = "DTM_PROJ_ID")
    private ProjMstrEntity projId;

    @Column(name = "DTM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "DTM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DTM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "DTM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DTM_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCompanyRep() {
        return companyRep;
    }

    public void setCompanyRep(String companyRep) {
        this.companyRep = companyRep;
    }

    public String getVendorRep() {
        return vendorRep;
    }

    public void setVendorRep(String vendorRep) {
        this.vendorRep = vendorRep;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorSign() {
        return vendorSign;
    }

    public void setVendorSign(String vendorSign) {
        this.vendorSign = vendorSign;
    }

    public String getVendorDesignation() {
        return vendorDesignation;
    }

    public void setVendorDesignation(String vendorDesignation) {
        this.vendorDesignation = vendorDesignation;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PreContractEntity getContractId() {
        return contractId;
    }

    public void setContractId(PreContractEntity contractId) {
        this.contractId = contractId;
    }

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
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

}