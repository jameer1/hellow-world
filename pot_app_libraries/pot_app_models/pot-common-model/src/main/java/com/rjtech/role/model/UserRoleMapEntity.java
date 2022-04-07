package com.rjtech.role.model;

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
import com.rjtech.user.model.UserEntity;

/**
 * The persistent class for the user_role_mstr database table.
 * 
 */
@Entity
@Table(name = "user_role_map")
public class UserRoleMapEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "URM_ID")
    private Long userRoleId;

    @ManyToOne
    @JoinColumn(name = "URM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "URM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "URM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "URM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "URM_UPDATED_ON")
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "URM_RLM_ID")
    private RoleMstrEntity roleMstr;

    @ManyToOne
    @JoinColumn(name = "URM_USR_ID")
    private UserEntity userEntity;

    public UserRoleMapEntity() {
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
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

    public RoleMstrEntity getRoleMstr() {
        return this.roleMstr;
    }

    public void setRoleMstr(RoleMstrEntity roleMstr) {
        this.roleMstr = roleMstr;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

}