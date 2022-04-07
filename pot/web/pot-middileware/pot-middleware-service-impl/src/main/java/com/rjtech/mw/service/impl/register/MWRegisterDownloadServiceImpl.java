package com.rjtech.mw.service.impl.register;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.mw.service.register.MWEmpRegisterService;
import com.rjtech.pot.mw.restconfig.service.impl.RestConfigServiceImpl;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.emp.req.EmpBankAccDeactivateReq;
import com.rjtech.register.emp.req.EmpBankAccountDetailsSaveReq;
import com.rjtech.register.emp.req.EmpChargeOutRateSaveReq;
import com.rjtech.register.emp.req.EmpContactSaveReeq;
import com.rjtech.register.emp.req.EmpContactsDeactiveReq;
import com.rjtech.register.emp.req.EmpEnrollmentGetReq;
import com.rjtech.register.emp.req.EmpLeaveAttendanceSaveReq;
import com.rjtech.register.emp.req.EmpLeaveReq;
import com.rjtech.register.emp.req.EmpLeaveReqApprSaveReq;
import com.rjtech.register.emp.req.EmpMedicalHistorySaveReq;
import com.rjtech.register.emp.req.EmpNokDeactiveReq;
import com.rjtech.register.emp.req.EmpNokSaveReq;
import com.rjtech.register.emp.req.EmpPayDeductionSaveReq;
import com.rjtech.register.emp.req.EmpPayRatesSaveReq;
import com.rjtech.register.emp.req.EmpProvidentFundSaveReq;
import com.rjtech.register.emp.req.EmpRegDeactivateReq;
import com.rjtech.register.emp.req.EmpRegisterGetReq;
import com.rjtech.register.emp.req.EmpRegisterReq;
import com.rjtech.register.emp.req.EmpRegisterSaveReq;
import com.rjtech.register.emp.req.EmpTransReq;
import com.rjtech.register.emp.req.EmpTransSaveReq;
import com.rjtech.register.emp.req.ManPowerGenderStatisticsReq;
import com.rjtech.register.emp.req.ProjEmpRegisterGetReq;
import com.rjtech.register.emp.req.ProjEmpRegistersSaveReq;
import com.rjtech.register.emp.req.ProjEmpServiceHistoryReq;
import com.rjtech.register.emp.resp.EmpBankAccountDtlResp;
import com.rjtech.register.emp.resp.EmpChargeOutRateResp;
import com.rjtech.register.emp.resp.EmpContactDetailsResp;
import com.rjtech.register.emp.resp.EmpEnrollmentResp;
import com.rjtech.register.emp.resp.EmpLeaveAttendanceYearResp;
import com.rjtech.register.emp.resp.EmpLeaveReqApprResp;
import com.rjtech.register.emp.resp.EmpMedicalHistoryResp;
import com.rjtech.register.emp.resp.EmpNokResp;
import com.rjtech.register.emp.resp.EmpNotificationsResp;
import com.rjtech.register.emp.resp.EmpPayDeductionResp;
import com.rjtech.register.emp.resp.EmpPaybleRateResp;
import com.rjtech.register.emp.resp.EmpProvidentFundResp;
import com.rjtech.register.emp.resp.EmpRegResp;
import com.rjtech.register.emp.resp.EmpRegisterOnLoadResp;
import com.rjtech.register.emp.resp.EmpRegisterResp;
import com.rjtech.register.emp.resp.EmpReqTransOnLoadResp;
import com.rjtech.register.emp.resp.EmpServiceHistoryResp;
import com.rjtech.register.manpower.reports.dto.ManPowerGenderStatistics;
import com.rjtech.register.manpower.reports.dto.ManPowerMobilizationStatistics;
import com.rjtech.register.plant.req.PlantRegisterGetReq;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.register.emp.req.EmpQualificationRecordsSaveReq;
import com.rjtech.register.emp.resp.EmpQualificationRecordsResp;
import com.rjtech.mw.service.register.MWRegisterDownloadService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;
import com.rjtech.constants.ApplicationConstants;

@Service(value = "mwRegisterDownloadService")
@RJSService(modulecode = "mwRegisterDownloadService")
@Transactional
public class MWRegisterDownloadServiceImpl extends RestConfigServiceImpl implements MWRegisterDownloadService {
    
    public ResponseEntity<ByteArrayResource> downloadRegisterDocs( Long recordId, String category ) {
	    Map<String, Object> paramsMap = new HashMap<>();
	    String recId = String.valueOf(recordId);
	    paramsMap.put("recordId", recordId);
	    paramsMap.put("category", category);
	    ResponseEntity<String[]> response = null;
	    String url = AppUtils.getUrl(
	    		getRegisterExchangeUrl(
	    				RegisterURLConstants.REGISTER_PARH_URL + RegisterURLConstants.DOWNLOAD_REGISTER_DOCS),
	            paramsMap);
	    System.out.println(url);
	    response = constructGETRestTemplate( url, String[].class );
	    String type="";
	    String[] resp = response.getBody();
	    System.out.println(response.getBody());
	    String fileBasePath = resp[3];
	    System.out.println(fileBasePath);
	    Path fileName = null;    
        byte[] fileBytes = null;
        try
        {
        	Path path = Paths.get(fileBasePath);
    	    fileBytes = Files.readAllBytes(path);
    	    type = Files.probeContentType(path);
            fileName = path.getFileName();             
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
	    return ResponseEntity.ok().contentType(MediaType.parseMediaType(type))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(fileBytes));
    }
}
