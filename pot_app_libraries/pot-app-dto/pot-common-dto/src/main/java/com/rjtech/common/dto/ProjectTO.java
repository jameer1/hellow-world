package com.rjtech.common.dto;

public class ProjectTO extends ClientTO {

    private static final long serialVersionUID = -3763356942184775462L;

    private Long projId;
    private String code;
    private String name;
    private String parentCode;
    private String parentName;
    private Long parentProjId;

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