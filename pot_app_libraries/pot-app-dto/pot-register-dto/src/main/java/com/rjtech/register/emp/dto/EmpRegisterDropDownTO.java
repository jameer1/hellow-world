package com.rjtech.register.emp.dto;

import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;

public class EmpRegisterDropDownTO {

    public EmpRegisterDropDownTO() {

    }

    List<String> gender;

    List<String> locality;

    List<String> employeeType;

    private Map<Long, LabelKeyTO> empCategoryMap;

    public List<String> getGender() {
        return gender;
    }

    public List<String> getLocality() {
        return locality;
    }

    public List<String> getEmployeeType() {
        return employeeType;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }

    public void setLocality(List<String> locality) {
        this.locality = locality;
    }

    public void setEmployeeType(List<String> employeeType) {
        this.employeeType = employeeType;
    }

    public Map<Long, LabelKeyTO> getEmpCategoryMap() {
        return empCategoryMap;
    }

    public void setEmpCategoryMap(Map<Long, LabelKeyTO> empCategoryMap) {
        this.empCategoryMap = empCategoryMap;
    }

}
