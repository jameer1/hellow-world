package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.SchofEstimatesApprTO;

public class ProjSchofEstimatesResp extends AppResp {
	
	 private static final long serialVersionUID = 1L;
	 private List<SchofEstimatesApprTO> schofEstimatesApprTOs = null;
	 
	 public ProjSchofEstimatesResp() {
		 schofEstimatesApprTOs = new ArrayList<SchofEstimatesApprTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	 }
	 
	 public List<SchofEstimatesApprTO> getSchofEstimatesApprTOs(){
		 return schofEstimatesApprTOs;
	 }
	 
	 public void setSchofEstimatesApprTOs(List<SchofEstimatesApprTO> schofEstimatesApprTOs) {
		 this.schofEstimatesApprTOs = schofEstimatesApprTOs;
	 }
}