package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.reports.MWReportsPlantService;
import com.rjtech.plant.reports.req.CurrentPlantsReportGetReq;
import com.rjtech.plant.reports.req.PlantCostCodeWiseGetReq;
import com.rjtech.plant.reports.req.PlantDateWiseAcutalHrsGetReq;
import com.rjtech.plant.reports.req.PlantIdleHrsRecordsGetReq;
import com.rjtech.plant.reports.req.PlantPeriodicalAcutalHrsGetReq;
import com.rjtech.plant.reports.req.PlantUtilizationRecordsGetReq;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mwReportsPlantService")
@RJSService(modulecode = "mwReportsPlantService")
@Transactional
public class MWReportsPlantServiceImpl extends RestConfigServiceImpl implements MWReportsPlantService {

    public ReportsResp getPlantDateWiseActualHrsReport(PlantDateWiseAcutalHrsGetReq plantDateWiseAcutalHrsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(plantDateWiseAcutalHrsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PLANT_DATE_WISE_ACTUAL_HRS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getPlantPeriodicalActualHrsReport(
            PlantPeriodicalAcutalHrsGetReq plantPeriodicalAcutalHrsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(plantPeriodicalAcutalHrsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PLANT_PERIODICAL_ACTUAL_HOURS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getPlantUtilisationRecordsReport(PlantUtilizationRecordsGetReq plantUtilizationRecordsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(plantUtilizationRecordsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PLANT_UTILISATION_RECORDS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getCurrentPlantsReport(CurrentPlantsReportGetReq currentPlantsReportGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(currentPlantsReportGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_CURRENT_PLANTS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getPlantCostCodeWiseReport(PlantCostCodeWiseGetReq plantCostCodeWiseGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(plantCostCodeWiseGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PLANT_COST_CODE_WISE_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getPlantIdleHoursReport(PlantIdleHrsRecordsGetReq plantIdleHrsRecordsGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(plantIdleHrsRecordsGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_PLANT_IDLE_HOURS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

}
