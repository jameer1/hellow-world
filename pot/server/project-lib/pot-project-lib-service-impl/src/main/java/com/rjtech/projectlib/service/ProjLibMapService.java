package com.rjtech.projectlib.service;

import com.rjtech.common.resp.LabelKeyTOMapResp;
import com.rjtech.projectlib.req.ProjCrewGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.req.ProjSOWItemGetReq;
import com.rjtech.projectlib.resp.ProjLibMapResp;
import com.rjtech.projectlib.resp.ProjLibUniqueMapResp;
import com.rjtech.projectlib.resp.ProjSowItemsMapResp;

public interface ProjLibMapService {

    ProjLibMapResp ProjEmpClassMap(ProjCrewGetReq projCrewGetReq);

    ProjLibMapResp ProjStockPileMap(ProjCrewGetReq projCrewGetReq);

    ProjLibMapResp ProjWorkShiftMap(ProjCrewGetReq projCrewGetReq);

    ProjLibMapResp ProjCrewListMap(ProjCrewGetReq projCrewGetReq);

    ProjLibMapResp ProjSOEMap(ProjCrewGetReq projCrewGetReq);

    ProjLibMapResp ProjSORMap(ProjCrewGetReq projCrewGetReq);

    ProjLibMapResp ProjCostCodeMap(ProjCrewGetReq projCrewGetReq);

    ProjLibMapResp getEpsProjectMap(ProjGetReq projGetReq);

    ProjLibMapResp getEpsListMap(ProjGetReq projGetReq);

    ProjLibUniqueMapResp ProjPlantClassMap(ProjCrewGetReq projCrewGetReq);

    LabelKeyTOMapResp getMultiProjCodeMap(ProjGetReq projGetReq);

    LabelKeyTOMapResp getMultiEpsCodeMap(ProjGetReq projGetReq);

    LabelKeyTOMapResp getMultiProjSOWItemMap(ProjGetReq projGetReq);

    LabelKeyTOMapResp getMultiProjSORItemMap(ProjGetReq projGetReq);

    LabelKeyTOMapResp getMultiProjCostCodeMap(ProjGetReq projGetReq);

}
