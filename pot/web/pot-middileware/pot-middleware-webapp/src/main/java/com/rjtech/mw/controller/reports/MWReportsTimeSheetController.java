package com.rjtech.mw.controller.reports;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.mw.service.projlib.MWProjLibMapService;
import com.rjtech.mw.service.reports.MWReportsTimeSheetService;
import com.rjtech.mw.service.user.MWUserService;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.reports.constants.ReportsURLConstants;
import com.rjtech.reports.resp.ReportsResp;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.timesheet.reports.req.DailyTimeSheetGetReq;
import com.rjtech.timesheet.reports.req.TimeSheetApprStatusGetReq;
import com.rjtech.timesheet.reports.req.TimeSheetReqUserGetReq;
import com.rjtech.user.dto.UserTO;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.resp.UserResp;

@RestController
@RequestMapping(ReportsURLConstants.REPORTS_PARH_URL)
public class MWReportsTimeSheetController {

    @Autowired
    private MWReportsTimeSheetService mwReportsTimeSheetService;

    @Autowired
    private MWUserService mwUserService;

    @Autowired
    private MWProjLibMapService mwProjLibMapService;

    @RequestMapping(value = ReportsURLConstants.GET_TIMESHEET_DAILY_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getTimeSheetDailyReport(@RequestBody DailyTimeSheetGetReq dailyTimeSheetGetReq) {
        ReportsResp reportsResp = mwReportsTimeSheetService.getTimeSheetDailyReport(dailyTimeSheetGetReq);
        reportsResp.setUsersMap(getUsersMap());
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
    }

    @RequestMapping(value = ReportsURLConstants.GET_TIMESHEET_APPROVESTATUS_REPORT, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getTimeSheetApprStatusReport(
            @RequestBody TimeSheetApprStatusGetReq timeSheetApprStatusGetReq) {
        ReportsResp reportsResp = mwReportsTimeSheetService.getTimeSheetApprStatusReport(timeSheetApprStatusGetReq);
        ProjGetReq projGetReq = new ProjGetReq();
        projGetReq.setProjIds(timeSheetApprStatusGetReq.getProjIds());
        reportsResp.setUserProjMap(mwProjLibMapService.getMultiProjCodeMap(projGetReq).getLabelKeyTOList());
        Map<Long, LabelKeyTO> usersMap = getUsersMap();
        reportsResp.setUsersMap(usersMap);
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
    }

    private Map<Long, LabelKeyTO> getUsersMap() {
        ClientReq clientReq = new ClientReq();
        clientReq.setStatus(StatusCodes.ACTIVE.getValue());
        clientReq.setClientId(AppUserUtils.getClientId());

        UserResp userResp = mwUserService.getUsers(clientReq);
        Map<Long, LabelKeyTO> usersMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO usersLabelKeyTO = null;
        for (UserTO userTO : userResp.getUsers()) {

            usersLabelKeyTO = new LabelKeyTO();
            usersLabelKeyTO.setId(userTO.getUserId());
            usersLabelKeyTO.setCode(userTO.getUserName());
            usersLabelKeyTO.setName(userTO.getFirstName());
            usersLabelKeyTO.setUnitOfMeasure(userTO.getLastName());
            usersMap.put(userTO.getUserId(), usersLabelKeyTO);
        }
        return usersMap;
    }

    @RequestMapping(value = ReportsURLConstants.GET_TIMESHEET_REQ_USERS, method = RequestMethod.POST)
    public ResponseEntity<ReportsResp> getTimeSheetReqUserReport(
            @RequestBody TimeSheetReqUserGetReq timeSheetReqUserGetReq) {
        ReportsResp reportsResp = mwReportsTimeSheetService.getTimeSheetReqUserReport(timeSheetReqUserGetReq);
        return new ResponseEntity<ReportsResp>(reportsResp, HttpStatus.OK);
    }
}
