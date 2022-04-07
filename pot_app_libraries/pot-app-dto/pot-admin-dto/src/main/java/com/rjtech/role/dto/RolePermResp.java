package com.rjtech.role.dto;

import java.io.Serializable;

public class RolePermResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8949904430215967003L;
    
    private Long id;
    private String permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
