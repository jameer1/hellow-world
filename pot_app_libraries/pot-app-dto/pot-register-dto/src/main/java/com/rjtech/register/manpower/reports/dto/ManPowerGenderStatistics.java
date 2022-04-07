package com.rjtech.register.manpower.reports.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManPowerGenderStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long projId;
    private Long companyId;
    private Long empClassId;
    private String empCategoryName;

    private Long parentProjId;

    private String projName;
    private String companyName;
    private String parentProjName;
    private String empClassName;
    private String classificationPerClient;
    private String classificationPerUnion;

    private List<ManPowerGenderStatisticsEmpDetails> empDetails = new ArrayList<>();

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

    public String getEmpCategoryName() {
        return empCategoryName;
    }

    public void setEmpCategoryName(String empCategoryName) {
        this.empCategoryName = empCategoryName;
    }

    public Long getParentProjId() {
        return parentProjId;
    }

    public void setParentProjId(Long parentProjId) {
        this.parentProjId = parentProjId;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getParentProjName() {
        return parentProjName;
    }

    public void setParentProjName(String parentProjName) {
        this.parentProjName = parentProjName;
    }

    public String getEmpClassName() {
        return empClassName;
    }

    public void setEmpClassName(String empClassName) {
        this.empClassName = empClassName;
    }

    public List<ManPowerGenderStatisticsEmpDetails> getEmpDetails() {
        return empDetails;
    }

    public void setEmpDetails(List<ManPowerGenderStatisticsEmpDetails> empDetails) {
        this.empDetails = empDetails;
    }

    public String getClassificationPerClient() {
        return classificationPerClient;
    }

    public void setClassificationPerClient(String classificationPerClient) {
        this.classificationPerClient = classificationPerClient;
    }

    public String getClassificationPerUnion() {
        return classificationPerUnion;
    }

    public void setClassificationPerUnion(String classificationPerUnion) {
        this.classificationPerUnion = classificationPerUnion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
        result = prime * result + ((empCategoryName == null) ? 0 : empCategoryName.hashCode());
        result = prime * result + ((empClassId == null) ? 0 : empClassId.hashCode());
        result = prime * result + ((projId == null) ? 0 : projId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ManPowerGenderStatistics other = (ManPowerGenderStatistics) obj;
        	return ((empClassName.equalsIgnoreCase(other.empClassName))&&(parentProjName.equalsIgnoreCase(other.parentProjName)));
    }

}
