package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.PeriodicalScheduleMaintenanceDtlTO;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceDeleteReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceGetReq;
import com.rjtech.register.fixedassets.req.PeriodicalScheduleMaintenanceSaveReq;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.fixedassets.resp.PeriodicalScheduleMaintenanceResp;

public interface PeriodicalScheduleMaintenanceService {

    PeriodicalScheduleMaintenanceResp getPeriodicalScheduleMaintenance(
            PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq);
    
    public void saveFixedAssetsPeriodicals(MultipartFile filePlan,MultipartFile fileActual,
            PeriodicalScheduleMaintenanceSaveReq periodicalScheduleMaintenanceSaveReq);

    void periodicalScheduleMaintenanceDelete(
            PeriodicalScheduleMaintenanceDeleteReq periodicalScheduleMaintenanceDeleteReq);

    PeriodicalScheduleMaintenanceDtlTO planDocDownloadFile(Long fileId);

    PeriodicalScheduleMaintenanceDtlTO actualDocDownloadFile(Long fileId);
    
    DocumentsResp getProjPeriodicalDocuemnts(
            PeriodicalScheduleMaintenanceGetReq periodicalScheduleMaintenanceGetReq);

}
