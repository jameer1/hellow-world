package com.rjtech.projsettings.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjChangeOrderDetailsTO;

public class ChangeOrderDetailsSaveReq extends ProjectTO{
	
		    private List<ProjChangeOrderDetailsTO>   projChangeOrderDetailsTOs =  new ArrayList<ProjChangeOrderDetailsTO>(
		            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

			public List<ProjChangeOrderDetailsTO> getProjChangeOrderDetailsTOs() {
				return projChangeOrderDetailsTOs;
			}

			public void setProjChangeOrderDetailsTOs(List<ProjChangeOrderDetailsTO> projChangeOrderDetailsTOs) {
				this.projChangeOrderDetailsTOs = projChangeOrderDetailsTOs;
			}
		    

}
