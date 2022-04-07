package com.rjtech.projsettings.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.notification.model.EmployeeNotificationsEntity;
//import com.rjtech.notification.model.remove.EmployeeNotificationsEntityCopy;
//import com.rjtech.notification.model.EmployeeNotificationsEntityCopy;
//import com.rjtech.notification.model.remove.EmployeeNotificationsEntityCopy;

@Entity
@Table(name = "proj_emp_transfer_approval")
@NamedQuery(name = "EmpTransAddtionalTimeApprEntity.findAll", query = "SELECT p FROM EmpTransAddtionalTimeApprEntity p")
public class EmpTransAddtionalTimeApprEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETA_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETA_DATE")
    private Date requisitionDate;

    // Recheck if this business is correct
    @ManyToOne
    @JoinColumn(name = "ETA_NTF_ID")
    private EmployeeNotificationsEntity employeeNotificationsEntity;

    @Column(name = "ETA_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ETA_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETA_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "ETA_IS_LATEST")
    private boolean latest;

    @ManyToOne
    @JoinColumn(name = "ETA_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ETA_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "ETA_PET_ID")
    private EmpTransNormalTimeEntity empTransNormalTimeEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(Date requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public EmployeeNotificationsEntity getEmployeeNotificationsEntity() {
        return employeeNotificationsEntity;
    }

    public void setEmployeeNotificationsEntity(EmployeeNotificationsEntity employeeNotificationsEntity) {
        this.employeeNotificationsEntity = employeeNotificationsEntity;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
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

    public EmpTransNormalTimeEntity getEmpTransNormalTimeEntity() {
        return empTransNormalTimeEntity;
    }

    public void setEmpTransNormalTimeEntity(EmpTransNormalTimeEntity empTransNormalTimeEntity) {
        this.empTransNormalTimeEntity = empTransNormalTimeEntity;
    }

}
