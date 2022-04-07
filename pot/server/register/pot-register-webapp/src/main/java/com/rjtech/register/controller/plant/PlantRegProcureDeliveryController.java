package com.rjtech.register.controller.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.dto.PlantPODocketDtlTO;
import com.rjtech.register.plant.model.PlantProjPODtlEntity;
import com.rjtech.register.plant.req.PlantProjPODeliverySaveReq;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantProjPODeliveryResp;
import com.rjtech.register.service.plant.PlantPOService;
import java.io.IOException;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class PlantRegProcureDeliveryController {

    @Autowired
    private PlantPOService regPlantPOService;

    @RequestMapping(value = RegisterURLConstants.GET_REG_PLANT_PROCURE_DELIVERY, method = RequestMethod.POST)
    public ResponseEntity<PlantProjPODeliveryResp> getRegPlantProcureDeliveryDetails(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        return new ResponseEntity<>(regPlantPOService.getPlantProjectPODtls(plantProjectDtlGetReq), HttpStatus.OK);
    }

    /*@RequestMapping(value = RegisterURLConstants.SAVE_REG_PLANT_PROCURE_DELIVERY, method = RequestMethod.POST)
    public ResponseEntity<PlantProjPODeliveryResp> saveRegPlantProcureDelivery( String plantProcureDeliveryStr, MultipartFile files[] ) throws IOException {
    	System.out.println("saveRegPlantProcureDelivery function from PlantRegProcureDeliveryController");
        PlantProjPODeliverySaveReq plantProjectPOSaveReq = AppUtils.fromJson( plantProcureDeliveryStr, PlantProjPODeliverySaveReq.class );
        //PlantProjPODtlEntity plantProjPODtlEntity = regPlantPOService.savePlantProjectPODtls( plantProjectPOSaveReq,
        //        files );
        regPlantPOService.savePlantProjectPODtls( plantProjectPOSaveReq, files );
        PlantProjectDtlGetReq plantProjectDtlGetReq = new PlantProjectDtlGetReq();
        plantProjectDtlGetReq.setPlantId(plantProjectPOSaveReq.getPlantId());
        plantProjectDtlGetReq.setProjId(plantProjectPOSaveReq.getProjId());
        plantProjectDtlGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        //plantProjectDtlGetReq.setPlantProjPODtlId(plantProjPODtlEntity.getId());
        PlantProjPODeliveryResp plantProjectDtlResp = regPlantPOService.getPlantProjectPODtls( plantProjectDtlGetReq );
        //return new ResponseEntity<>(plantProjectDtlResp, HttpStatus.OK);    	
        //return new ResponseEntity<PreContractCmpDocResp>(preContractCmpDocResp, HttpStatus.OK);
        return new ResponseEntity<PlantProjPODeliveryResp>(plantProjectDtlResp, HttpStatus.OK);
    }*/
    @RequestMapping(value = RegisterURLConstants.SAVE_REG_PLANT_PROCURE_DELIVERY, method = RequestMethod.POST)
    public ResponseEntity<PlantProjPODeliveryResp> saveRegPlantProcureDelivery( String plantProcureDeliveryStr, MultipartFile files[] ) throws IOException {
    	System.out.println("saveRegPlantProcureDelivery function from PlantRegProcureDeliveryController");
        PlantProjPODeliverySaveReq plantProjectPOSaveReq = AppUtils.fromJson( plantProcureDeliveryStr, PlantProjPODeliverySaveReq.class );
        PlantProjPODeliveryResp plantProjPODeliveryResp = regPlantPOService.savePlantProjectPODtls( plantProjectPOSaveReq, files );        
        return new ResponseEntity<PlantProjPODeliveryResp>( plantProjPODeliveryResp, HttpStatus.OK );
    }

    @GetMapping(value = RegisterURLConstants.DOWNLOAD_REG_PLANT_PROCURE_DELIVERY)
    public ResponseEntity<ByteArrayResource> downloadRegPlantProcureDeliveryDoc(
            @RequestParam("plantPOId") long plantPOId) {

        PlantPODocketDtlTO respTO = regPlantPOService.downloadPlantDocment(plantPOId);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(respTO.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + respTO.getDocName() + "\"")
                .body(new ByteArrayResource(respTO.getDocContent()));
    }

}
