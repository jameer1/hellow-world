package com.rjtech.calendar.req;

import java.util.List;

import com.rjtech.common.dto.ProjectTO;

public class CalDeactiveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -1165124423124218730L;
    private List<Long> id;

    public List<Long> getId() {
        return id;
    }

    public void setId(List<Long> id) {
        this.id = id;
    }

}
