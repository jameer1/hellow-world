package com.rjtech.register.service.plant;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.plant.dto.PlantPODocketDtlTO;
import com.rjtech.register.plant.model.PlantProjPODtlEntity;
import com.rjtech.register.plant.req.PlantProjPODeliverySaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantProjPODeliveryResp;
import java.io.IOException;

public interface PlantPOService {

    PlantProjPODeliveryResp getPlantProjectPODtls(PlantProjectDtlGetReq plantProjectDtlGetReq);

    //PlantProjPODtlEntity savePlantProjectPODtls( PlantProjPODeliverySaveReq plantProjectPOSaveReq, MultipartFile[] files ) throws IOException;
    PlantProjPODeliveryResp savePlantProjectPODtls( PlantProjPODeliverySaveReq plantProjectPOSaveReq, MultipartFile[] files ) throws IOException;

    PlantPODocketDtlTO downloadPlantDocment(long plantPOId);

}
