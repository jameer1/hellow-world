package com.rjtech.projsettings.req;

import java.util.Date;

import com.rjtech.common.dto.ProjectTO;

public class ProjProgressGetReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = -8694753774457885006L;
    private Date toDate;
    private Date fromDate;

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

}
