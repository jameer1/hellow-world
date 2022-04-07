package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ResourceBudgetTO;

public class ProjResourceBudgetResp extends AppResp {
	
	 private static final long serialVersionUID = 1L;
	 private List<ResourceBudgetTO> resourceBudgetTOs = null;
	 
	 public ProjResourceBudgetResp() {
		 resourceBudgetTOs = new ArrayList<ResourceBudgetTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	 }
	 
	 public List<ResourceBudgetTO> getResourceBudgetTOs(){
		 return resourceBudgetTOs;
	 }
	 
	 public void setResourceBudgetTOs(List<ResourceBudgetTO> resourceBudgetTOs) {
		 this.resourceBudgetTOs = resourceBudgetTOs;
	 }
	 //path: 
}