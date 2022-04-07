package com.rjtech.user.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.register.emp.model.EmpRegisterDtlEntity;
//import com.rjtech.register.model.EmpRegisterDtlEntityCopy;
import com.rjtech.role.model.UserRoleMapEntity;

/**
 * The persistent class for the user_mstr database table.
 * 
 */
@Entity
@Table(name = "user_mstr")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "USR_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USR_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "USR_DISPLAY_NAME")
    private String displayName;

    @Column(name = "USR_EMAIL")
    private String email;

    @Column(name = "USR_EMP_CODE")
    private String empCode;

    @Column(name = "USR_EMP_DESG")
    private String empDesg;

    @Column(name = "USR_FIRST_NAME")
    private String firstName;

    @Column(name = "USR_LAST_NAME")
    private String lastName;

    @Column(name = "USR_MOBILE_NUM")
    private String mobileNo;

    @Column(name = "USR_PASSWORD")
    private String password;

    @Column(name = "USR_PHONE_NUM")
    private String phoneNo;

    @ManyToOne
    @JoinColumn(name = "USR_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USR_UPDATED_ON")
    private Date updatedOn;

    @Column(name = "USR_USER_NAME")
    private String userName;

    @Column(name = "USR_STATUS")
    private Integer status;

    @Column(name = "USR_TYPE")
    private Integer userType;

    @Column(name = "USR_REMARKS")
    private String remarks;

    @OneToOne
    @JoinColumn(name = "USR_ERD_ID")
    private EmpRegisterDtlEntity empRegId;

    @ManyToOne
    @JoinColumn(name = "USR_CRM_ID", updatable = false)
    private ClientRegMstrEntity clientRegMstrEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRoleMapEntity> userRoleMstrs = new ArrayList<UserRoleMapEntity>();

    @Column(name = "IS_CLIENT", columnDefinition = "int default 0")
    private boolean client;

    public UserEntity() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpDesg() {
        return empDesg;
    }

    public void setEmpDesg(String empDesg) {
        this.empDesg = empDesg;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public ClientRegMstrEntity getClientRegMstrEntity() {
        return clientRegMstrEntity;
    }

    public void setClientRegMstrEntity(ClientRegMstrEntity clientRegMstrEntity) {
        this.clientRegMstrEntity = clientRegMstrEntity;
    }

    public List<UserRoleMapEntity> getUserRoleMstrs() {
        return this.userRoleMstrs;
    }

    public void setUserRoleMstrs(List<UserRoleMapEntity> userRoleMstrs) {
        this.userRoleMstrs = userRoleMstrs;
    }

    public UserRoleMapEntity addUserRoleMstr(UserRoleMapEntity userRoleMstr) {
        getUserRoleMstrs().add(userRoleMstr);
        userRoleMstr.setUserEntity(this);

        return userRoleMstr;
    }

    public UserRoleMapEntity removeUserRoleMstr(UserRoleMapEntity userRoleMstr) {
        getUserRoleMstrs().remove(userRoleMstr);
        userRoleMstr.setUserEntity(null);

        return userRoleMstr;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public EmpRegisterDtlEntity getEmpRegId() {
        return empRegId;
    }

    public void setEmpRegId(EmpRegisterDtlEntity empRegId) {
        this.empRegId = empRegId;
    }

    public boolean isClient() {
        return client;
    }

    public void setClient(boolean client) {
        this.client = client;
    }

}