package com.rjtech.register.emp.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjectTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmpRegisterDtlTO extends ProjectTO {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String lastName;
    private Long cmpId;
    private String cmpCode;
    private String cmpName;
    private String gender;
    private String dob;
    private Long empClassId;
    private String empCode;
    private String empClassName;
    private Long procurecatgId;
    private String procurecatgName;
    private String location;
    private String empStatus;

    private String phoneNumber;

    private Long prevProjId;

    private LabelKeyTO procureLabelKeyTO = new LabelKeyTO();

    private ProjEmpRegisterTO projEmpRegisterTO = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCmpId() {
        return cmpId;
    }

    public void setCmpId(Long cmpId) {
        this.cmpId = cmpId;
    }

    public String getCmpCode() {
        return cmpCode;
    }

    public void setCmpCode(String cmpCode) {
        this.cmpCode = cmpCode;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Long getEmpClassId() {
        return empClassId;
    }

    public void setEmpClassId(Long empClassId) {
        this.empClassId = empClassId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpClassName() {
        return empClassName;
    }

    public void setEmpClassName(String empClassName) {
        this.empClassName = empClassName;
    }

    public Long getProcurecatgId() {
        return procurecatgId;
    }

    public void setProcurecatgId(Long procurecatgId) {
        this.procurecatgId = procurecatgId;
    }

    public String getProcurecatgName() {
        return procurecatgName;
    }

    public void setProcurecatgName(String procurecatgName) {
        this.procurecatgName = procurecatgName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    public LabelKeyTO getProcureLabelKeyTO() {
        return procureLabelKeyTO;
    }

    public void setProcureLabelKeyTO(LabelKeyTO procureLabelKeyTO) {
        this.procureLabelKeyTO = procureLabelKeyTO;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPrevProjId() {
        return prevProjId;
    }

    public void setPrevProjId(Long prevProjId) {
        this.prevProjId = prevProjId;
    }

    public ProjEmpRegisterTO getProjEmpRegisterTO() {
        return projEmpRegisterTO;
    }

    public void setProjEmpRegisterTO(ProjEmpRegisterTO projEmpRegisterTO) {
        this.projEmpRegisterTO = projEmpRegisterTO;
    }

}