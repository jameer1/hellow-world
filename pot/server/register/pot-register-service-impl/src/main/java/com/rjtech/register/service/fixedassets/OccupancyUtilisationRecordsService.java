package com.rjtech.register.service.fixedassets;

import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsDeleteReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsGetReq;
import com.rjtech.register.fixedassets.req.OccupancyUtilisationRecordsSaveReq;
import com.rjtech.register.fixedassets.resp.OccupancyUtilisationRecordsResp;

public interface OccupancyUtilisationRecordsService {

    OccupancyUtilisationRecordsResp getOccupancyUtilisationRecords(
            OccupancyUtilisationRecordsGetReq occupancyUtilisationRecordsGetReq);

    void saveOccupancyUtilisationRecords(OccupancyUtilisationRecordsSaveReq occupancyUtilisationRecordsSaveReq);

    void occupancyUtilisationRecordsDelete(OccupancyUtilisationRecordsDeleteReq occupancyUtilisationRecordsDeleteReq);

}
