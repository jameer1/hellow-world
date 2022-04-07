package com.rjtech.projectlib.resp;

import com.rjtech.projectlib.dto.ProjCrewTO;

public class ProjCrewListOnLoadResp {
    private ProjWorkShiftResp projWorkShiftResp = new ProjWorkShiftResp();
    private ProjCrewTO projCrewTO = null;

    /*	private List<ProjCrewTO> projCrewTOs=null;
    */
    public ProjCrewListOnLoadResp() {
        projCrewTO = new ProjCrewTO();
        /*projCrewTOs=new ArrayList<ProjCrewTO>();*/
    }

    public ProjWorkShiftResp getProjWorkShiftResp() {
        return projWorkShiftResp;
    }

    public void setProjWorkShiftResp(ProjWorkShiftResp projWorkShiftResp) {
        this.projWorkShiftResp = projWorkShiftResp;
    }

    public ProjCrewTO getProjCrewTO() {
        return projCrewTO;
    }

    public void setProjCrewTO(ProjCrewTO projCrewTO) {
        this.projCrewTO = projCrewTO;
    }

    /*public List<ProjCrewTO> getProjCrewTOs() {
    	return projCrewTOs;
    }
    
    public void setProjCrewTOs(List<ProjCrewTO> projCrewTOs) {
    	this.projCrewTOs = projCrewTOs;
    }*/

}
