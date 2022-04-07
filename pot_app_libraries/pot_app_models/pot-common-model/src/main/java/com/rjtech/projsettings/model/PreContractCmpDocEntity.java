package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the pre_contract_cmp_documents database table.
 * 
 */
@Entity
@Table(name = "pre_contract_cmp_documents")
public class PreContractCmpDocEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3417039034872080640L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PCCD_ID")
    private Long id;

    @Column(name = "PCCD_NAME")
    private String name;

    @Column(name = "PCCD_TYPE")
    private String mimeType;

    @Column(name = "PCCD_DATE")
    private Date date;

    @Column(name = "PCCD_VERSION")
    private Integer version;

    @Column(name = "PCCD_FROM_VENDOR")
    private boolean fromVendor;

    @Column(name = "PCCD_MODE_TYPE")
    private String modeType;

    @Column(name = "PCCD_SENDER")
    private String sender;

    @Column(name = "PCCD_RECEIVER")
    private String receiver;

    @Column(name = "PCCD_STATUS")
    private Integer status;

    @Column(name = "PCCD_CREATED_BY", updatable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCCD_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "PCCD_UPDATED_BY")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PCCD_UPDATED_ON")
    private Date updatedOn;

    @Lob
    @Column(name = "FILE_CONTENT")
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "PCCD_PCC_ID")
    private PreContractsCmpEntity preContractsCmpEntity;

    public PreContractCmpDocEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean isFromVendor() {
        return fromVendor;
    }

    public void setFromVendor(boolean fromVendor) {
        this.fromVendor = fromVendor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public PreContractsCmpEntity getPreContractsCmpEntity() {
        return preContractsCmpEntity;
    }

    public void setPreContractsCmpEntity(PreContractsCmpEntity preContractsCmpEntity) {
        this.preContractsCmpEntity = preContractsCmpEntity;
    }

}