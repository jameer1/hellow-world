package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.SchofRatesApprTO;

public class SchofRatesSaveReq extends ProjectTO {
	
	 private List<SchofRatesApprTO> schofRatesAppTOs = new ArrayList<SchofRatesApprTO>(
	            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	 
	 public List<SchofRatesApprTO> getSchofRatesApprTOs(){
		 return schofRatesAppTOs;
	 }
	 
	 public void setSchofRatesApprTOs(List<SchofRatesApprTO> schofRatesAppTOs) {
		 this.schofRatesAppTOs  = schofRatesAppTOs;
	 }
}