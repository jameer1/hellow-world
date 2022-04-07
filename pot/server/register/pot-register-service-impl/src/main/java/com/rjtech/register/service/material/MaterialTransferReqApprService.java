package com.rjtech.register.service.material;

import com.rjtech.common.resp.AppResp;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.material.dto.MaterialProjDocketTO;
import com.rjtech.register.material.req.MaterialNotificationReq;
import com.rjtech.register.material.req.MaterialTransReq;
import com.rjtech.register.material.req.MaterialTransferReqApprSaveReq;
import com.rjtech.register.material.resp.MaterialNotificationResp;
import com.rjtech.register.material.resp.MaterialTransferReqApprResp;

public interface MaterialTransferReqApprService {

    MaterialTransferReqApprResp getMaterialTransfers(MaterialTransReq materialTransReq);

    MaterialTransferReqApprResp getMaterialTransferDetails(MaterialTransReq materialTransReq);

    LabelKeyTOResp getMaterialDetailsForTransfer(MaterialTransReq materialTransReq);

    AppResp saveMaterialTransfers(MaterialTransferReqApprSaveReq materialTransferReqApprSaveReq);

    MaterialNotificationResp getMaterialNotifications(NotificationsGetReq notificationsGetReq);

    public LabelKeyTOResp getProjSettingsMaterialTransferCheck(MaterialTransReq materialTransReq);
    
    public MaterialProjDocketTO getMaterialsForProjDocket(MaterialNotificationReq materialNotificationReq);

}
