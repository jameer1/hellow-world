package com.rjtech.mw.service.impl.reports;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.reports.MWReportsTimeSheetService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.timesheet.reports.req.DailyTimeSheetGetReq;
import com.rjtech.timesheet.reports.req.TimeSheetApprStatusGetReq;
import com.rjtech.timesheet.reports.req.TimeSheetReqUserGetReq;

@Service(value = "mwReportsTimeSheetService")
@RJSService(modulecode = "mwReportsTimeSheetService")
@Transactional
public class MWReportsTimeSheetServiceImpl extends RestConfigServiceImpl implements MWReportsTimeSheetService {

    public ReportsResp getTimeSheetDailyReport(DailyTimeSheetGetReq dailyTimeSheetGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(dailyTimeSheetGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_TIMESHEET_DAILY_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getTimeSheetApprStatusReport(TimeSheetApprStatusGetReq timeSheetApprStatusGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(timeSheetApprStatusGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_TIMESHEET_APPROVESTATUS_REPORT);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

    public ReportsResp getTimeSheetReqUserReport(TimeSheetReqUserGetReq timeSheetReqUserGetReq) {
        ResponseEntity<String> strResponse = null;
        strResponse = getReportsPOSTRestTemplate(AppUtils.toJson(timeSheetReqUserGetReq),
                ReportsURLConstants.REPORTS_PARH_URL + ReportsURLConstants.GET_TIMESHEET_REQ_USERS);
        return AppUtils.fromJson(strResponse.getBody(), ReportsResp.class);
    }

}
