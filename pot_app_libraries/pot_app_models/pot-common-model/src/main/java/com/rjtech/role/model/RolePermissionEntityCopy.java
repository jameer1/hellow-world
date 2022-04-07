package com.rjtech.role.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.rjtech.common.model.ClientRegEntity;

@Entity
@Table(name = "ROLE_PERMISSION")
public class RolePermissionEntityCopy {

    @Id
    @GeneratedValue
    @Column(name = "RP_ID")
    private long id;

    @Column(name = "RP_PERMISSION")
    private String permission;

    @ManyToOne
    @JoinColumn(name = "RP_RLM_ID")
    private RoleMstrEntityCopy roleMstrEntity;

    @ManyToOne
    @JoinColumn(name = "RP_CRM_ID", updatable = false)
    private ClientRegEntity clientId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public RoleMstrEntityCopy getRoleMstrEntity() {
        return roleMstrEntity;
    }

    public void setRoleMstrEntity(RoleMstrEntityCopy roleMstrEntity) {
        this.roleMstrEntity = roleMstrEntity;
    }

    public ClientRegEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientRegEntity clientId) {
        this.clientId = clientId;
    }

}
