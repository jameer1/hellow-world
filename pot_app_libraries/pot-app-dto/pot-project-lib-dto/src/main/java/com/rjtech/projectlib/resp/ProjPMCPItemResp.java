package com.rjtech.projectlib.resp;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.dto.CostReportTO;
import com.rjtech.projectlib.dto.PeriodCostTO;
import com.rjtech.projectlib.dto.ProjCostStmtDtlTOCopy;
import com.rjtech.projectlib.dto.ProjPMCMItemTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjPMCPItemResp extends AppResp {

    private static final long serialVersionUID = 1L;

    private List<PeriodCostTO> periodCostTOs = new ArrayList<>();

    private List<CostReportTO> costReportResps = new ArrayList<>();
    //----------
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

    // below item is used at Actual Cost % Module
    HashMap<String,PeriodCostTO> actualCostModuleMap = null;

    public ProjPMCPItemResp() {
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

    public List<PeriodCostTO> getPeriodCostTOs() {
        return periodCostTOs;
    }

    public void setPeriodCostTOs(List<PeriodCostTO> periodCostTOs) {
        this.periodCostTOs = periodCostTOs;
    }

    public List<CostReportTO> getCostReportResps() {
        return costReportResps;
    }

    public void setCostReportResps(List<CostReportTO> costReportResps) {
        this.costReportResps = costReportResps;
    }

    public List<ProjPMCMItemTO> getProjSORItemTOs() {
        return projSORItemTOs;
    }

    public void setProjSORItemTOs(List<ProjPMCMItemTO> projSORItemTOs) {
        this.projSORItemTOs = projSORItemTOs;
    }

    public HashMap<String, PeriodCostTO> getActualCostModuleMap() {
        return actualCostModuleMap;
    }

    public void setActualCostModuleMap(HashMap<String, PeriodCostTO> actualCostModuleMap) {
        this.actualCostModuleMap = actualCostModuleMap;
    }
}
