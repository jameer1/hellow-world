package com.rjtech.timemanagement.workdairy.dto;

import java.util.Date;

import com.rjtech.timemanagement.timesheet.dto.TimesheetReportTO;

public class WorkDairyApprStatusReportTO extends TimesheetReportTO {

    private boolean clientApproval;

    private Date date;

    public boolean isClientApproval() {
        return clientApproval;
    }

    public void setClientApproval(boolean clientApproval) {
        this.clientApproval = clientApproval;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
