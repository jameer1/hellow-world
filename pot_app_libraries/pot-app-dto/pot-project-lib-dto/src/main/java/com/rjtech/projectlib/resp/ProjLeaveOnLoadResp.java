package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.projectlib.dto.ProjLeaveTypeTO;

public class ProjLeaveOnLoadResp {

    private ProjLeavePaidResp projLeavePaidResp = new ProjLeavePaidResp();
    private ProjEmpTypeResp projEmpTypeResp = new ProjEmpTypeResp();
    private ProjCostItemResp projCostItemResp = new ProjCostItemResp();
    private ProjLeaveTypeTO projLeaveTypeTO = null;
    /*private List<ProjLeaveTypeTO> projLeaveTypeTOs=null ;*/

    public ProjLeaveOnLoadResp() {
        projLeaveTypeTO = new ProjLeaveTypeTO();
        /*projLeaveTypeTOs=new ArrayList<ProjLeaveTypeTO>();*/
    }

    public ProjLeavePaidResp getProjLeavePaidResp() {
        return projLeavePaidResp;
    }

    public void setProjLeavePaidResp(ProjLeavePaidResp projLeavePaidResp) {
        this.projLeavePaidResp = projLeavePaidResp;
    }

    public ProjEmpTypeResp getProjEmpTypeResp() {
        return projEmpTypeResp;
    }

    public void setProjEmpTypeResp(ProjEmpTypeResp projEmpTypeResp) {
        this.projEmpTypeResp = projEmpTypeResp;
    }

    public ProjCostItemResp getProjCostItemResp() {
        return projCostItemResp;
    }

    public void setProjCostItemResp(ProjCostItemResp projCostItemResp) {
        this.projCostItemResp = projCostItemResp;
    }

    public ProjLeaveTypeTO getProjLeaveTypeTO() {
        return projLeaveTypeTO;
    }

    public void setProjLeaveTypeTO(ProjLeaveTypeTO projLeaveTypeTO) {
        this.projLeaveTypeTO = projLeaveTypeTO;
    }

    /*public List<ProjLeaveTypeTO> getProjLeaveTypeTOs() {
    	return projLeaveTypeTOs;
    }
    
    public void setProjLeaveTypeTOs(List<ProjLeaveTypeTO> projLeaveTypeTOs) {
    	this.projLeaveTypeTOs = projLeaveTypeTOs;
    }*/

}
