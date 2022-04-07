package com.rjtech.mw.service.reports;

import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.timesheet.reports.req.DailyTimeSheetGetReq;
import com.rjtech.timesheet.reports.req.TimeSheetApprStatusGetReq;
import com.rjtech.timesheet.reports.req.TimeSheetReqUserGetReq;

public interface MWReportsTimeSheetService {

    ReportsResp getTimeSheetDailyReport(DailyTimeSheetGetReq dailyTimeSheetGetReq);

    ReportsResp getTimeSheetApprStatusReport(TimeSheetApprStatusGetReq timeSheetApprStatusGetReq);

    ReportsResp getTimeSheetReqUserReport(TimeSheetReqUserGetReq timeSheetReqUserGetReq);

}
