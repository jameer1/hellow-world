package com.rjtech.common.dto;

public class EmployeeTypeRecordTo extends ClientTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Integer status;
    private String procCtgCode;
    private String procCtgType;
    private String procCtgName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getProcCtgCode() {
        return procCtgCode;
    }
    public void setProcCtgCode(String procCtgCode) {
        this.procCtgCode = procCtgCode;
    }
    public String getProcCtgType() {
        return procCtgType;
    }
    public void setProcCtgType(String procCtgType) {
        this.procCtgType = procCtgType;
    }
    public String getProcCtgName() {
        return procCtgName;
    }
    public void setProcCtgName(String procCtgName) {
        this.procCtgName = procCtgName;
    }
   

}
