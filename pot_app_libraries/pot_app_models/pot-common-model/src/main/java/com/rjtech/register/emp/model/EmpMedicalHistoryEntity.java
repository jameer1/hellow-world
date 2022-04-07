package com.rjtech.register.emp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.model.ProjDocFileEntity;

@Entity
@Table(name = "emp_medical_history")
public class EmpMedicalHistoryEntity implements Serializable {

    private static final long serialVersionUID = 5127759126297571266L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMH_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EMH_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @Column(name = "EMH_RECORD_ID")
    private String recordId;

    @Column(name = "EMH_ITEM")
    private String item;

    @Column(name = "EMH_PARTICULARS")
    private String particular;

    @Column(name = "EMH_COMMENTS")
    private String comments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMH_FROM_DATE")
    private Date fromDate;

    @Column(name = "EMH_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "EMH_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMH_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "EMH_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "EMH_UPDATED_ON")
    private Timestamp updatedOn;

    @ManyToOne
    @JoinColumn(name = "EMH_PER_ID")
    private EmpProjRigisterEntity empProjRigisterEntity;
    
    @OneToOne
    @JoinColumn(name = "PDFL_ID_FK")
    private ProjDocFileEntity projDocFileEntity;
    
    @Column(name = "FILE_NAME")
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpRegisterDtlEntity getEmpRegisterDtlEntity() {
        return empRegisterDtlEntity;
    }

    public void setEmpRegisterDtlEntity(EmpRegisterDtlEntity empRegisterDtlEntity) {
        this.empRegisterDtlEntity = empRegisterDtlEntity;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public EmpProjRigisterEntity getEmpProjRigisterEntity() {
        return empProjRigisterEntity;
    }

    public void setEmpProjRigisterEntity(EmpProjRigisterEntity empProjRigisterEntity) {
        this.empProjRigisterEntity = empProjRigisterEntity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ProjDocFileEntity getProjDocFileEntity() {
    	return projDocFileEntity;
    }
    
    public void setProjDocFileEntity( ProjDocFileEntity projDocFileEntity ) {
    	this.projDocFileEntity = projDocFileEntity;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName ) {
        this.fileName = fileName;
    }
}
