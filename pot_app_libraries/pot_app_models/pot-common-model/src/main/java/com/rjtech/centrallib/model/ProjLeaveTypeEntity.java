package com.rjtech.centrallib.model;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * 
 * UI Reference : Enterprise/ Central Library / Leave Type and Attendance Record
 * Codes
 * 
 * @author vsdpradeep.kalla
 *
 */

@Entity
@Table(name = "proj_leave_type_mstr", uniqueConstraints = @UniqueConstraint(columnNames = { "PJL_LVM_CODE",
        "PJL_COUNTRY_CODE", "PJL_EFFECTIVE_FROM", "PJL_CRM_ID" }))
public class ProjLeaveTypeEntity implements Serializable {

    private static final long serialVersionUID = -4039919593137375667L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PJL_ID")
    private Long id;

    @Column(name = "PJL_LVM_CODE")
    private String code;

    @Column(name = "PJL_LVM_NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PJL_CRM_ID", updatable = false)
    private ClientRegEntity clientRegEntity;

    @Column(name = "PJL_PRIOR_APPROVAL")
    private boolean priorApproval;

    @Column(name = "PJL_MEDICAL_FORM")
    private boolean medicalForm;

    @Column(name = "PJL_EVIDENCE_FORM")
    private boolean evidenceForm;

    @Column(name = "PJL_MAX_ALLOW_YEAR")
    private Integer maxAllowYear;

    @Column(name = "PJL_MAX_ALLOW_EVENT")
    private Integer maxAllowEvent;

    @Column(name = "PJL_STATUS")
    private Integer status;

    @Column(name = "PJL_COUNTRY_CODE")
    private String countryCode;

    @Column(name = "PJL_EFFECTIVE_FROM")
    private Date effectiveFrom;

    @OneToMany(mappedBy = "projLeaveTypeEntity", cascade = CascadeType.ALL)
    private List<ProjLeaveCategoryType> projLeaveCategoryTypes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PJL_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJL_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PJL_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PJL_UPDATED_ON")
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientRegEntity getClientRegEntity() {
        return clientRegEntity;
    }

    public void setClientRegEntity(ClientRegEntity clientRegEntity) {
        this.clientRegEntity = clientRegEntity;
    }

    public boolean isPriorApproval() {
        return priorApproval;
    }

    public void setPriorApproval(boolean priorApproval) {
        this.priorApproval = priorApproval;
    }

    public boolean isMedicalForm() {
        return medicalForm;
    }

    public void setMedicalForm(boolean medicalForm) {
        this.medicalForm = medicalForm;
    }

    public boolean isEvidenceForm() {
        return evidenceForm;
    }

    public void setEvidenceForm(boolean evidenceForm) {
        this.evidenceForm = evidenceForm;
    }

    public Integer getMaxAllowYear() {
        return maxAllowYear;
    }

    public void setMaxAllowYear(Integer maxAllowYear) {
        this.maxAllowYear = maxAllowYear;
    }

    public Integer getMaxAllowEvent() {
        return maxAllowEvent;
    }

    public void setMaxAllowEvent(Integer maxAllowEvent) {
        this.maxAllowEvent = maxAllowEvent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public List<ProjLeaveCategoryType> getProjLeaveCategoryTypes() {
        return projLeaveCategoryTypes;
    }

    public void setProjLeaveCategoryTypes(List<ProjLeaveCategoryType> projLeaveCategoryTypes) {
        this.projLeaveCategoryTypes = projLeaveCategoryTypes;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
