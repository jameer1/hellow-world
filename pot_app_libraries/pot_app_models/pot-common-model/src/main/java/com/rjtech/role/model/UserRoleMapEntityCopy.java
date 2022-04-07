package com.rjtech.role.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the user_role_mstr database table.
 * 
 */
@Entity
@Table(name = "user_role_map")
public class UserRoleMapEntityCopy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "URM_ID")
    private Long userRoleId;

    @Column(name = "URM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "URM_RLM_ID")
    private RoleMstrEntityCopy roleMstr;

    @ManyToOne
    @JoinColumn(name = "URM_USR_ID")
    private UserMstrEntity userEntity;

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RoleMstrEntityCopy getRoleMstr() {
        return roleMstr;
    }

    public void setRoleMstr(RoleMstrEntityCopy roleMstr) {
        this.roleMstr = roleMstr;
    }

    public UserMstrEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserMstrEntity userEntity) {
        this.userEntity = userEntity;
    }

}
