package com.rjtech.mw.service.reports;

import com.rjtech.plant.reports.req.CurrentPlantsReportGetReq;
import com.rjtech.plant.reports.req.PlantCostCodeWiseGetReq;
import com.rjtech.plant.reports.req.PlantDateWiseAcutalHrsGetReq;
import com.rjtech.plant.reports.req.PlantIdleHrsRecordsGetReq;
import com.rjtech.plant.reports.req.PlantPeriodicalAcutalHrsGetReq;
import com.rjtech.plant.reports.req.PlantUtilizationRecordsGetReq;
import com.rjtech.reports.resp.ReportsResp;

public interface MWReportsPlantService {

    ReportsResp getPlantDateWiseActualHrsReport(PlantDateWiseAcutalHrsGetReq plantDateWiseAcutalHrsGetReq);

    ReportsResp getPlantPeriodicalActualHrsReport(PlantPeriodicalAcutalHrsGetReq plantPeriodicalAcutalHrsGetReq);

    ReportsResp getPlantUtilisationRecordsReport(PlantUtilizationRecordsGetReq plantUtilizationRecordsGetReq);

    ReportsResp getCurrentPlantsReport(CurrentPlantsReportGetReq currentPlantsReportGetReq);

    ReportsResp getPlantCostCodeWiseReport(PlantCostCodeWiseGetReq plantCostCodeWiseGetReq);

    ReportsResp getPlantIdleHoursReport(PlantIdleHrsRecordsGetReq plantIdleHrsRecordsGetReq);
}
