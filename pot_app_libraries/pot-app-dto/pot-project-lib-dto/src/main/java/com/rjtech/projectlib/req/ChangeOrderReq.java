package com.rjtech.projectlib.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.dto.CoMaterialTO;
import com.rjtech.projectlib.dto.CoProjCostTO;
import com.rjtech.projectlib.dto.CoProjManpowerTO;
import com.rjtech.projectlib.dto.CoProjPlantTO;
import com.rjtech.projectlib.dto.CoProjSOWTO;

public class ChangeOrderReq extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private List<ChangeOrderTO> changeOrderTOs = new ArrayList<ChangeOrderTO>();
    private List<CoProjManpowerTO> coProjManpowerTOs = new ArrayList<CoProjManpowerTO>();
    private List<CoProjPlantTO> coProjPlantsTOs = new ArrayList<CoProjPlantTO>();
    private List<CoProjSOWTO> coProjSOWTOs = new ArrayList<CoProjSOWTO>();
    private List<CoMaterialTO> coMaterialTOs = new ArrayList<CoMaterialTO>();
    private List<CoProjCostTO> coProjCostTOs = new ArrayList<CoProjCostTO>();
   
    public List<CoProjSOWTO> getCoProjSOWTOs() {
		return coProjSOWTOs;
	}

	public void setCoProjSOWTOs(List<CoProjSOWTO> coProjSOWTOs) {
		this.coProjSOWTOs = coProjSOWTOs;
	}

	private String fetchType;

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
    
    public String getFetchType() {
    	return fetchType;
    }
    
    public void setFetchType( String fetchType ) {
    	this.fetchType = fetchType;
    }
    
    public List<CoProjPlantTO> getCoProjPlantsTOs() {
        return coProjPlantsTOs;
    }

    public void setCoProjPlantsTOs( List<CoProjPlantTO> coProjPlantsTOs ) {
        this.coProjPlantsTOs = coProjPlantsTOs;
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
