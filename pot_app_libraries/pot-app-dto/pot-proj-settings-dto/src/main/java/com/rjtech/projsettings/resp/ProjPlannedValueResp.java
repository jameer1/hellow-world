package com.rjtech.projsettings.resp;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.resp.AppResp;
import com.rjtech.projsettings.dto.ProjPlannedValueTO;

public class ProjPlannedValueResp extends AppResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5070291468280350417L;
	private List<ProjPlannedValueTO> projPlannedValueTO = new ArrayList<ProjPlannedValueTO>();

	public List<ProjPlannedValueTO> getProjPlannedValueTO() {
		return projPlannedValueTO;
	}

	public void setProjPlannedValueTO(List<ProjPlannedValueTO> projPlannedValueTO) {
		this.projPlannedValueTO = projPlannedValueTO;
	}

}
