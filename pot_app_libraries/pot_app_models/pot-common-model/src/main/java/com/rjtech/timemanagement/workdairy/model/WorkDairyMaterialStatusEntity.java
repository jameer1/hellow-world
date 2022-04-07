package com.rjtech.timemanagement.workdairy.model;

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

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "work_dairy_material_status")
public class WorkDairyMaterialStatusEntity implements Serializable {

    private static final long serialVersionUID = -4758282147435820494L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WDMS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WDMS_WDMD_ID")
    private WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity;

    @ManyToOne
    @JoinColumn(name = "WDMS_USR_ID")
    private UserMstrEntity userMstrEntity;

    @Column(name = "WDMS_COMMENTS")
    private String comments;

    @Column(name = "WDMS_APPR_STATUS")
    private String apprStatus;

    @Column(name = "WDMS_TOTAL")
    private double total;

    @Column(name = "WDMS_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMS_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMS_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WDMS_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WDMS_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "workDairyMaterialStatusEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkDairyMaterialCostEntity> workDairyMaterialCostEntities = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public UserMstrEntity getUserMstrEntity() {
        return userMstrEntity;
    }

    public void setUserMstrEntity(UserMstrEntity userMstrEntity) {
        this.userMstrEntity = userMstrEntity;
    }

    public String getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public WorkDairyMaterialDtlEntity getWorkDairyMaterialDtlEntity() {
        return workDairyMaterialDtlEntity;
    }

    public void setWorkDairyMaterialDtlEntity(WorkDairyMaterialDtlEntity workDairyMaterialDtlEntity) {
        this.workDairyMaterialDtlEntity = workDairyMaterialDtlEntity;
    }

    public List<WorkDairyMaterialCostEntity> getWorkDairyMaterialCostEntities() {
        return workDairyMaterialCostEntities;
    }

    public void setWorkDairyMaterialCostEntities(List<WorkDairyMaterialCostEntity> workDairyMaterialCostEntities) {
        this.workDairyMaterialCostEntities = workDairyMaterialCostEntities;
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