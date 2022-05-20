package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.dto.CoMaterialTO;
import com.rjtech.projectlib.dto.CoProjCostTO;
import com.rjtech.projectlib.dto.CoProjManpowerTO;
import com.rjtech.projectlib.dto.CoProjPlantTO;
import com.rjtech.projectlib.dto.CoProjSOWTO;

public class ChangeOrderResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ChangeOrderTO> changeOrderTOs = null;
    private List<CoProjManpowerTO> coProjManpowerTOs = null;
    private List<CoProjPlantTO> coProjPlantsTOs = null;
    private List<CoProjSOWTO> coProjSOWTO = null;
    private List<CoMaterialTO> coMaterialTOs = null;
    private List<CoProjCostTO> coProjCostTOs = null;

    public ChangeOrderResp() {
    	changeOrderTOs = new ArrayList<ChangeOrderTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	coProjManpowerTOs = new ArrayList<CoProjManpowerTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	coProjPlantsTOs = new ArrayList<CoProjPlantTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	coProjSOWTO = new ArrayList<CoProjSOWTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	coMaterialTOs = new ArrayList<CoMaterialTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	coProjCostTOs = new ArrayList<CoProjCostTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }
    
    public List<ChangeOrderTO> getChangeOrderTOs() {
        return changeOrderTOs;
    }

    public void setChangeOrderTOs( List<ChangeOrderTO> changeOrderTOs ) {
        this.changeOrderTOs = changeOrderTOs;
    }
    
    public List<CoProjManpowerTO> getCoProjManpowerTOs() {
        return coProjManpowerTOs;
    }

    public void setCoProjManpowerTOs( List<CoProjManpowerTO> coProjManpowerTOs ) {
        this.coProjManpowerTOs = coProjManpowerTOs;
    }
    
    public List<CoProjPlantTO> getCoProjPlantsTOs() {
        return coProjPlantsTOs;
    }

    public void setCoProjPlantsTOs( List<CoProjPlantTO> coProjPlantsTOs ) {
        this.coProjPlantsTOs = coProjPlantsTOs;
    }

	public List<CoProjSOWTO> getCoProjSOWTO() {
		return coProjSOWTO;
	}

	public void setCoProjSOWTO(List<CoProjSOWTO> coProjSOWTO) {
		this.coProjSOWTO = coProjSOWTO;
	}

	public List<CoMaterialTO> getCoMaterialTOs() {
		return coMaterialTOs;
	}

	public void setCoMaterialTOs(List<CoMaterialTO> coMaterialTOs) {
		this.coMaterialTOs = coMaterialTOs;
	}

	public List<CoProjCostTO> getCoProjCostTOs() {
		return coProjCostTOs;
	}

	public void setCoProjCostTOs(List<CoProjCostTO> coProjCostTOs) {
		this.coProjCostTOs = coProjCostTOs;
	}
}
