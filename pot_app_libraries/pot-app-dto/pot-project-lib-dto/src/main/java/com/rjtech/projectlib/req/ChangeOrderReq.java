package com.rjtech.projectlib.req;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.MeasureUnitTO;
import com.rjtech.common.dto.ProjectTO;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.dto.CoProjManpowerTO;
import com.rjtech.projectlib.dto.CoProjPlantTO;

public class ChangeOrderReq extends ProjectTO {
    private static final long serialVersionUID = 2950084862079755848L;

    private List<ChangeOrderTO> changeOrderTOs = new ArrayList<ChangeOrderTO>();
    private List<CoProjManpowerTO> coProjManpowerTOs = new ArrayList<CoProjManpowerTO>();
    private List<CoProjPlantTO> coProjPlantsTOs = new ArrayList<CoProjPlantTO>();
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
}
