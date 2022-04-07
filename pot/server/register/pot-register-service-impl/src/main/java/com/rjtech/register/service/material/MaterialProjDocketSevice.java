package com.rjtech.register.service.material;

import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.material.dto.MaterialProjDocketTO;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjDocketSaveReq;
import com.rjtech.register.material.req.MaterialTransReq;
import com.rjtech.register.material.resp.MaterialProjDocketResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;

public interface MaterialProjDocketSevice {

    MaterialProjDocketResp getMaterialProjDockets(MaterialFilterReq materialGetReq);

    MaterialProjDocketResp getMaterialProjDocketsByDockType(MaterialGetReq materialGetReq);

    MaterialSchItemsResp getMaterialSchItemsByProjDocket(MaterialGetReq materialGetReq);

    MaterialProjDocketTO saveMaterialProjDocket(MaterialProjDocketSaveReq materialProjDocketSaveReq);

    void saveMaterialProjDocketDraft(MaterialProjDocketSaveReq materialProjDocketSaveReq);

    LabelKeyTOResp getMaterialSchDetailsForTransfer(MaterialGetReq materialGetReq);
    
    MaterialProjDocketResp getMaterialProjDocketsByProjectId( MaterialGetReq materialGetReq );

}
