package com.rjtech.register.emp.dto;

import java.io.Serializable;

public class EmpPublicHolidayTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1636271030274355828L;

    private Long id;
    private Long empLeaveReqApprId;
    private String type;
    private String desc;
    private String date;
    private Integer status;

    public EmpPublicHolidayTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getEmpLeaveReqApprId() {
        return empLeaveReqApprId;
    }

    public void setEmpLeaveReqApprId(Long empLeaveReqApprId) {
        this.empLeaveReqApprId = empLeaveReqApprId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
