package com.rjtech.mw.controller.register.plant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;

import com.rjtech.mw.dto.resource.plant.MWPlantProcureDeliveryOnLoadResp;
import com.rjtech.mw.service.register.MWPlantRegisterService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.plant.req.PlantProjectDtlGetReq;
import com.rjtech.register.plant.resp.PlantProjPODeliveryResp;
import com.rjtech.register.plant.resp.PlantProcureDeliveryResp;
import com.rjtech.register.plant.req.PlantProjPODeliverySaveReq;
import com.rjtech.common.utils.AppUtils;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWPlantProcureDeliveryController {

    @Autowired
    private MWPlantRegisterService mwPlantRegisterService;

    @RequestMapping(value = RegisterURLConstants.GET_REG_PLANT_PROCURE_DELIVERY, method = RequestMethod.POST)
    public ResponseEntity<MWPlantProcureDeliveryOnLoadResp> getRegPlantProcureDeliveryDetails(
            @RequestBody PlantProjectDtlGetReq plantProjectDtlGetReq) {
        MWPlantProcureDeliveryOnLoadResp mwResp = new MWPlantProcureDeliveryOnLoadResp();
        PlantProjPODeliveryResp plantProjPODeliveryResp = mwPlantRegisterService
                .getPlantProcureDeliveryDtls(plantProjectDtlGetReq);
        mwResp.setPlantProjPODtlTO(plantProjPODeliveryResp.getPlantProjPODtlTO());
        return new ResponseEntity<MWPlantProcureDeliveryOnLoadResp>(mwResp, HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.DOWNLOAD_REG_PLANT_PROCURE_DELIVERY)
    public ResponseEntity<ByteArrayResource> downloadRegPlantProcureDeliveryDoc(
            @RequestParam("plantPOId") long plantPOId) {
        return mwPlantRegisterService.downloadPlantDocment(plantPOId);
    }

    @PostMapping(value = RegisterURLConstants.SAVE_REG_PLANT_PROCURE_DELIVERY)
    public ResponseEntity<PlantProcureDeliveryResp> savePlantProcureDelivery( MultipartFile[] files, String plantProcureDeliveryStr ) {
    	System.out.println("savePlantProcureDelivery function of MWPlantProcureDeliveryController");
    	PlantProjPODeliverySaveReq plantPODeliverySaveReq = AppUtils.fromJson( plantProcureDeliveryStr,
    			PlantProjPODeliverySaveReq.class );
        return new ResponseEntity<PlantProcureDeliveryResp>( 
        		mwPlantRegisterService.savePlantProcureDelivery( files, plantPODeliverySaveReq ), HttpStatus.OK );
    }
}
