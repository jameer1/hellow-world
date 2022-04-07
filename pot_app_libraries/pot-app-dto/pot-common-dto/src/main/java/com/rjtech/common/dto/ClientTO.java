package com.rjtech.common.dto;

import java.io.Serializable;

public abstract class ClientTO implements Serializable {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long clientId;
    private String clientCode;
    private String email;
    private Long projId;
    private String code;
    private String name;
    private String parentCode;
    private String parentName;
    private Long parentProjId;


    private String countryCode;
    private Integer status = new Integer(1);

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getParentProjId() {
        return parentProjId;
    }

    public void setParentProjId(Long parentProjId) {
        this.parentProjId = parentProjId;
    }
    public String toString() {
    	return "From ProjectTO --projId:"+projId+" code:"+code+" name:"+name+" parentName:"+parentName+" parentProjId:"+parentProjId;
    }


}