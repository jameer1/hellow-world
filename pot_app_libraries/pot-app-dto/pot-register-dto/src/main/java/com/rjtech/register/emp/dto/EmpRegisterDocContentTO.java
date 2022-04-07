package com.rjtech.register.emp.dto;

import com.rjtech.common.dto.ProjectTO;

public class EmpRegisterDocContentTO extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 521750741187403147L;
    private Long id;
    private Long refId;

    /*private String content;*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

}
