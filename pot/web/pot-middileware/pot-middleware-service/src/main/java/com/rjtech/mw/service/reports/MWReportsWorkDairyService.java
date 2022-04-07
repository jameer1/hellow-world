package com.rjtech.mw.service.reports;

import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.workdairy.reports.req.WorkDairyApprStatusGetReq;
import com.rjtech.workdairy.reports.req.WorkDairyDailyGetReq;

public interface MWReportsWorkDairyService {

    ReportsResp getWorkDairyDailyManpowerReport(WorkDairyDailyGetReq workDairyDailyGetReq);

    ReportsResp getWorkDairyDailyMaterialReport(WorkDairyDailyGetReq workDairyDailyGetReq);

    ReportsResp getWorkDairyDailyPlantReport(WorkDairyDailyGetReq workDairyDailyGetReq);

    ReportsResp getWorkDairyDailyProgressReport(WorkDairyDailyGetReq workDairyDailyGetReq);

    ReportsResp getWorkDairyApprStatusReport(WorkDairyApprStatusGetReq workDairyApprStatusGetReq);

}
