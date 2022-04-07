package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.SchofRatesApprTO;

public class ProjSchofRatesResp extends AppResp {
	
	 private static final long serialVersionUID = 1L;
	 private List<SchofRatesApprTO> schofRatesApprTOs = null;
	 
	 public ProjSchofRatesResp() {
		 schofRatesApprTOs = new ArrayList<SchofRatesApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	 }
	 
	 public List<SchofRatesApprTO> getSchofRatesApprTOs(){
		 return schofRatesApprTOs;
	 }
	 
	 public void setSchofRatesApprTOs(List<SchofRatesApprTO> schofRatesApprTOs) {
		 this.schofRatesApprTOs = schofRatesApprTOs;
	 }
}
//path: D:\dhanraj-git\POT_LATEST\pot\server\project-settings\pot-proj-settings-dto\src\main\java\com\rjtech\projsettings\resp