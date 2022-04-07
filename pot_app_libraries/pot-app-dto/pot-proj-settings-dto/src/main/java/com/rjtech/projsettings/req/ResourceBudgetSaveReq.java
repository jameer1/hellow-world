package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ResourceBudgetTO;

public class ResourceBudgetSaveReq extends ProjectTO {
	
	 private List<ResourceBudgetTO> resourceBudgetTOs = new ArrayList<ResourceBudgetTO>(
	            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	 
	 public List<ResourceBudgetTO> getResourceBudgetTOs(){
		 return resourceBudgetTOs;
	 }
	 
	 public void setSchofRatesApprTOs(List<ResourceBudgetTO> resourceBudgetTOs) {
		 this.resourceBudgetTOs  = resourceBudgetTOs;
	 }
	 
}