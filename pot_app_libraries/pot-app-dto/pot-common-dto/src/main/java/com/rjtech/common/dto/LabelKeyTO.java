package com.rjtech.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class LabelKeyTO implements Serializable {

    private static final long serialVersionUID = -5766432568491732692L;
    private Long id;
    private String code;
    private String name;
    private Long referenceId;
    private String email;
    private Date date;
    private String unitOfMeasure;
    private Map<String, String> displayNamesMap = new HashMap<String, String>();
    private List displayNameList;
    private String poDescription;
    private Date demobDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Map<String, String> getDisplayNamesMap() {
        return displayNamesMap;
    }

    public void setDisplayNamesMap(Map<String, String> displayNamesMap) {
        this.displayNamesMap = displayNamesMap;
    }

    public List getDisplayNameList() {
        return displayNameList;
    }

    public void setDisplayNameList(List displayNameList) {
        this.displayNameList = displayNameList;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPoDescription() {
        return poDescription;
    }
    
    public void setPoDescription(String poDescription) {
        this.poDescription = poDescription;
    }
    
    public void setDemobDate(Date demobDate) {
    	this.demobDate = demobDate;
    }
    
    public Date getDemobDate() {
    	return demobDate;
    }

    public String toString() {
    	return " id: "+id+" code: "+code+" name: "+name+" referenceId: "+referenceId+" email: "+email+" date: "+date+" unitOfMeasure: "+unitOfMeasure+" displayNamesMap: "+displayNamesMap
    			+" displayNameList: "+displayNameList+" poDescription: "+poDescription;
    }
}