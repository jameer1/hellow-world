package com.rjtech.procurement.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.ProcurementPoRepeatpoTO;

public class ProcurementPoRepeatpoGetReq extends AppResp {
	
	private static final long serialVersionUID = 1L;
	List<ProcurementPoRepeatpoTO> procurementRepeatPODtlTOs = new ArrayList<ProcurementPoRepeatpoTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
	
	public List<ProcurementPoRepeatpoTO> getProcurementRepeatPODtlTOs() {
		return procurementRepeatPODtlTOs;
	}
	public void setProcurementRepeatPODtlTOs(List<ProcurementPoRepeatpoTO> procurementRepeatPODtlTOs) {
		this.procurementRepeatPODtlTOs = procurementRepeatPODtlTOs;
	}
	
	

}
