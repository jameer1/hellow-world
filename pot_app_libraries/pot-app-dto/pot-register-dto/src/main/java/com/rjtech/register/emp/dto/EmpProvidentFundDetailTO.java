package com.rjtech.register.emp.dto;

import java.io.Serializable;

public class EmpProvidentFundDetailTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1005295865625030463L;
    private Long id;
    private Long providentFundId;
    private String desc;
    private String details;
    private String comments;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvidentFundId() {
        return providentFundId;
    }

    public void setProvidentFundId(Long providentFundId) {
        this.providentFundId = providentFundId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
