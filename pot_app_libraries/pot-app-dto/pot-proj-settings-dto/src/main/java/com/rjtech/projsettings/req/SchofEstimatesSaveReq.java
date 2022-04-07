package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.SchofEstimatesApprTO;

public class SchofEstimatesSaveReq extends ProjectTO {
	
	 private List<SchofEstimatesApprTO> schofEstimatesAppTOs = new ArrayList<SchofEstimatesApprTO>(
	            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	 
	 public List<SchofEstimatesApprTO> getSchofEstimatesApprTOs(){
		 return schofEstimatesAppTOs;
	 }
	 
	 public void setSchofEstimatesApprTOs(List<SchofEstimatesApprTO> schofEstimatesAppTOs) {
		 this.schofEstimatesAppTOs  = schofEstimatesAppTOs;
	 }
}