package com.rjtech.projectlib.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ChangeOrderTO;
import com.rjtech.projectlib.dto.CoProjManpowerTO;
import com.rjtech.projectlib.dto.CoProjPlantTO;

public class ChangeOrderResp extends AppResp {
    private static final long serialVersionUID = -6671175298681215590L;
    private List<ChangeOrderTO> changeOrderTOs = null;
    private List<CoProjManpowerTO> coProjManpowerTOs = null;
    private List<CoProjPlantTO> coProjPlantsTOs = null;

    public ChangeOrderResp() {
    	changeOrderTOs = new ArrayList<ChangeOrderTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	coProjManpowerTOs = new ArrayList<CoProjManpowerTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    	coProjPlantsTOs = new ArrayList<CoProjPlantTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
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
}
