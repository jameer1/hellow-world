package com.rjtech.notification.model;

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
import com.rjtech.projectlib.model.ProjCrewMstrEntity;

/**
 * The persistent class for the proj_emp_attendence_mstr database table.
 * 
 */
@Entity
@Table(name = "proj_emp_attendence_mstr")
public class EmpAttendanceMstrEntityCopy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEAM_ID")
    private Long id;

    @Column(name = "PEAM_NAME")
    private String code;

    @ManyToOne
    @JoinColumn(name = "PEAM_EPM_ID")
    private ProjMstrEntity projId;

    @ManyToOne
    @JoinColumn(name = "PEAM_CRW_ID")
    private ProjCrewMstrEntity crewId;

    @Column(name = "PEAM_COPY_TEMPLATE")
    private boolean copyTemplate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEAM_MONTH")
    private Date attendenceMonth;

    @Column(name = "PEAM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "PEAM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEAM_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "PEAM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PEAM_UPDATED_ON")
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

    public boolean isCopyTemplate() {
        return copyTemplate;
    }

    public void setCopyTemplate(boolean copyTemplate) {
        this.copyTemplate = copyTemplate;
    }

    public Date getAttendenceMonth() {
        return attendenceMonth;
    }

    public void setAttendenceMonth(Date attendenceMonth) {
        this.attendenceMonth = attendenceMonth;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public ProjMstrEntity getProjId() {
        return projId;
    }

    public void setProjId(ProjMstrEntity projId) {
        this.projId = projId;
    }

    public ProjCrewMstrEntity getCrewId() {
        return crewId;
    }

    public void setCrewId(ProjCrewMstrEntity crewId) {
        this.crewId = crewId;
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