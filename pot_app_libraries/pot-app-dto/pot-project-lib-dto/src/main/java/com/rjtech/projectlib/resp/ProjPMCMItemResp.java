package com.rjtech.projectlib.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.ProjCostStmtDtlTOCopy;
import com.rjtech.projectlib.dto.ProjPMCMItemTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjPMCMItemResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs = null;

    public List<ProjCostStmtDtlTOCopy> getProjCostStmtDtlTOs() {
        return projCostStmtDtlTOs;
    }

    public void setProjCostStmtDtlTOs(List<ProjCostStmtDtlTOCopy> projCostStmtDtlTOs) {
        this.projCostStmtDtlTOs = projCostStmtDtlTOs;
    }
    ///
    private List<ProjPMCMItemTO> projSORItemTOs = null;

    private HashMap<String, String> projCostItemEntitiesMap = null;

    private List<ProjPMCMItemTO>  projPMCMReportTOList = null;

    public ProjPMCMItemResp() {
        projSORItemTOs = new ArrayList<ProjPMCMItemTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        projCostStmtDtlTOs = new ArrayList<>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<ProjPMCMItemTO> getProjPMCMItemTOs() {
        return projSORItemTOs;
    }

    public void setProjPMCMItemTOs(List<ProjPMCMItemTO> projPMCMItemTOs) {
        this.projSORItemTOs = projPMCMItemTOs;
    }

    public HashMap<String, String> getProjCostItemEntitiesMap() {
        return projCostItemEntitiesMap;
    }

    public void setProjCostItemEntitiesMap(HashMap<String, String> projCostItemEntitiesMap) {
        this.projCostItemEntitiesMap = projCostItemEntitiesMap;
    }

    public List<ProjPMCMItemTO> getProjPMCMReportTOList() {
        return projPMCMReportTOList;
    }

    public void setProjPMCMReportTOList(List<ProjPMCMItemTO> projPMCMReportTOList) {
        this.projPMCMReportTOList = projPMCMReportTOList;
    }
}
