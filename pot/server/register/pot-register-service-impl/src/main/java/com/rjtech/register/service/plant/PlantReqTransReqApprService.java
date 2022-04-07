package com.rjtech.register.service.plant;

import java.util.List;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.dto.NotificationsGetReq;
import com.rjtech.register.plant.req.PlantTranferReqApprSaveReq;
import com.rjtech.register.plant.req.PlantTransReq;
import com.rjtech.register.plant.resp.PlantNotificationResp;
import com.rjtech.register.plant.resp.PlantTransferReqApprResp;

public interface PlantReqTransReqApprService {

    List<LabelKeyTO> getStatusTypes();

    PlantNotificationResp getPlantNotifications(NotificationsGetReq notificationsGetReq);

    PlantTransferReqApprResp getPlantTransfers(PlantTransReq plantTransReq);

    PlantTransferReqApprResp getPlantTransferDetails(PlantTransReq plantTransReq);

    LabelKeyTOResp getPlantTransferReqDetails(PlantTransReq plantTransReq);

    PlantTransferReqApprResp savePlantTransfers(PlantTranferReqApprSaveReq plantReqForTransSaveReq);

    public LabelKeyTOResp getProjSettingsPlantTransferCheck(PlantTransReq plantTransReq);

}
