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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;

@Entity
@Table(name = "emp_nok_contact_dtl")
public class EmpNokEntity implements Serializable {
    private static final long serialVersionUID = 7994060621221035224L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENC_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ENC_ERD_ID")
    private EmpRegisterDtlEntity empRegisterDtlEntity;

    @Column(name = "ENC_CONTACT_TYPE")
    private String contactType;

    @Column(name = "ENC_FNAME")
    private String firstName;

    @Column(name = "ENC_LNAME")
    private String lastName;

    @Column(name = "ENC_RESIDENTIAL_ADDR")
    private String residentialAddr;

    @Column(name = "ENC_POSTAL_ADDR")
    private String postalAddr;

    @Column(name = "ENC_RELATION_WITH_EMP")
    private String relationWithEmp;

    @Column(name = "ENC_PHN_NUM")
    private String phoneNumber;

    @Column(name = "ENC_ALT_PHN_NUM")
    private String alternatePhoneNumber;

    @Column(name = "ENC_EMAIL")
    private String email;

    @Column(name = "ENC_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ENC_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENC_CREATED_ON", updatable = false)
    private Date createdOn;

    @ManyToOne
    @JoinColumn(name = "ENC_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Column(name = "ENC_UPDATED_ON")
    private Timestamp updatedOn;

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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResidentialAddr() {
        return residentialAddr;
    }

    public void setResidentialAddr(String residentialAddr) {
        this.residentialAddr = residentialAddr;
    }

    public String getPostalAddr() {
        return postalAddr;
    }

    public void setPostalAddr(String postalAddr) {
        this.postalAddr = postalAddr;
    }

    public String getRelationWithEmp() {
        return relationWithEmp;
    }

    public void setRelationWithEmp(String relationWithEmp) {
        this.relationWithEmp = relationWithEmp;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternatePhoneNumber() {
        return alternatePhoneNumber;
    }

    public void setAlternatePhoneNumber(String alternatePhoneNumber) {
        this.alternatePhoneNumber = alternatePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
