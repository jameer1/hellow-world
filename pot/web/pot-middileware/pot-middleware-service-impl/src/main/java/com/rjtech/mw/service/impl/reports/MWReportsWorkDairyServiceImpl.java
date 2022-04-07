package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.reports.MWReportsWorkDairyService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.workdairy.reports.req.WorkDairyApprStatusGetReq;
import com.rjtech.workdairy.reports.req.WorkDairyDailyGetReq;

@Service(value = "mwReportsWorkDairyService")
@RJSService(modulecode = "mwReportsWorkDairyService")
@Transactional
public class MWReportsWorkDairyServiceImpl extends RestConfigServiceImpl implements MWReportsWorkDairyService {

    public ReportsResp getWorkDairyDailyManpowerReport(WorkDairyDailyGetReq workDairyDailyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(workDairyDailyGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_WORKDAIRY_DAILY_MANPOWER_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getWorkDairyDailyMaterialReport(WorkDairyDailyGetReq workDairyDailyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(workDairyDailyGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_WORKDAIRY_DAILY_MATERIAL_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getWorkDairyDailyPlantReport(WorkDairyDailyGetReq workDairyDailyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(workDairyDailyGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_WORKDAIRY_DAILY_PLANT_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getWorkDairyDailyProgressReport(WorkDairyDailyGetReq workDairyDailyGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(workDairyDailyGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_WORKDAIRY_DAILY_PROGRESS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getWorkDairyApprStatusReport(WorkDairyApprStatusGetReq workDairyApprStatusGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(workDairyApprStatusGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_WORKDAIRY_APPROVESTATUS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

}
