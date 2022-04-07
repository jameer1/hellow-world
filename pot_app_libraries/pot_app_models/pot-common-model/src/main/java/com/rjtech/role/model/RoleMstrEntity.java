package com.rjtech.role.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;

/**
 * The persistent class for the role_mstr database table.
 * 
 * NOTE: Don't change the package or class name of this class, we are comparing
 * the class name in HibernateInterceptor
 * 
 */
@Entity
@Table(name = "role_mstr")
public class RoleMstrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RLM_ID")
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "RLM_CREATED_BY", updatable = false)
    private UserMstrEntity createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RLM_CREATED_ON", updatable = false)
    private Date createdOn;

    @Column(name = "RLM_NAME")
    private String roleName;

    @Column(name = "RLM_DEFAULT", columnDefinition = "int default 0")
    private Boolean defaultRole;

    @Column(name = "RLM_STATUS")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "RLM_UPDATED_BY")
    private UserMstrEntity updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RLM_UPDATED_ON")
    private Date updatedOn;

    @OneToMany(mappedBy = "roleMstrEntity", orphanRemoval = true)
    private List<RolePermissionEntity> permissions;

    @ManyToOne
    @JoinColumn(name = "RLM_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    @OneToMany(mappedBy = "roleMstr")
    private List<UserRoleMapEntity> userRoleMapEntities;

    private transient boolean edit;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public List<RolePermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermissionEntity> permissions) {
        this.permissions = permissions;
    }

    public List<UserRoleMapEntity> getUserRoleMapEntities() {
        return userRoleMapEntities;
    }

    public void setUserRoleMapEntities(List<UserRoleMapEntity> userRoleMapEntities) {
        this.userRoleMapEntities = userRoleMapEntities;
    }

    public UserRoleMapEntity addUserRoleMstr(UserRoleMapEntity userRoleMstr) {
        getUserRoleMapEntities().add(userRoleMstr);
        userRoleMstr.setRoleMstr(this);

        return userRoleMstr;
    }

    public UserRoleMapEntity removeUserRoleMstr(UserRoleMapEntity userRoleMstr) {
        getUserRoleMapEntities().remove(userRoleMstr);
        userRoleMstr.setRoleMstr(null);

        return userRoleMstr;
    }

    public UserMstrEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserMstrEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(Boolean defaultRole) {
        this.defaultRole = defaultRole;
    }

    public UserMstrEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserMstrEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}