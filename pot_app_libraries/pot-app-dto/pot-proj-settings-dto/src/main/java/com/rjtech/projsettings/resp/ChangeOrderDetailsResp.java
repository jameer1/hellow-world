package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projsettings.dto.ProjChangeOrderDetailsTO;


public class ChangeOrderDetailsResp extends AppResp {
	
	  private static final long serialVersionUID = 2872460880381019815L;

	    private List<ProjChangeOrderDetailsTO>   projChangeOrderDetailsTOs= null;
	    
	    public ChangeOrderDetailsResp()
	    {
	    	projChangeOrderDetailsTOs = new ArrayList<ProjChangeOrderDetailsTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	    }

		public List<ProjChangeOrderDetailsTO> getProjChangeOrderDetailsTOs() {
			return projChangeOrderDetailsTOs;
		}

		public void setProjChangeOrderDetailsTOs(List<ProjChangeOrderDetailsTO> projChangeOrderDetailsTOs) {
			this.projChangeOrderDetailsTOs = projChangeOrderDetailsTOs;
		}
	    
	    

}
