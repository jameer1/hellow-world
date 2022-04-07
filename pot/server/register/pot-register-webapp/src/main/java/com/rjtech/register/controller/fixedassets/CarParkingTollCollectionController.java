package com.rjtech.register.controller.fixedassets;

import java.io.IOException;

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
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.dto.CarParkingTollCollectionDtlTO;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionDeleteReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionGetReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionSaveReq;
import com.rjtech.register.fixedassets.resp.CarParkingTollCollectionResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;
import com.rjtech.register.service.fixedassets.CarParkingTollCollectionService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class CarParkingTollCollectionController {

    @Autowired
    private CarParkingTollCollectionService carParkingTollCollectionService;

    @RequestMapping(value = RegisterURLConstants.GET_CAR_PARKING_TOLL_COLLECTION, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> getCarParkingTollCollection(
            @RequestBody CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        CarParkingTollCollectionResp resp = carParkingTollCollectionService
                .getCarParkingTollCollection(carParkingTollCollectionGetReq);
        return new ResponseEntity<CarParkingTollCollectionResp>(resp, HttpStatus.OK);

    }
    
    //last record in metod
    
    @RequestMapping(value = RegisterURLConstants.GET_CAR_PARKING_TOLL_COLLECTION_LAST_RECORD, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> getCarParkingTollCollectionLastRecord(
            @RequestBody CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
    	System.out.println("controller MW");
        CarParkingTollCollectionResp resp = carParkingTollCollectionService
                .getCarParkingTollCollectionLastRecord(carParkingTollCollectionGetReq);
        return new ResponseEntity<CarParkingTollCollectionResp>(resp, HttpStatus.OK);

    }
    
    //==========================

    @RequestMapping(value = RegisterURLConstants.SAVE_CAR_PARKING_TOLL_COLLECTION, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> saveCarParkingTollCollection(MultipartFile file,
            String clientRegReq) throws IOException {
        CarParkingTollCollectionSaveReq carParkingTollCollectionSaveReq = AppUtils.fromJson(clientRegReq,
                CarParkingTollCollectionSaveReq.class);
        carParkingTollCollectionService.saveCarParkingTollCollection(file, carParkingTollCollectionSaveReq);
        CarParkingTollCollectionGetReq carParkingTollCollectionGetReq = new CarParkingTollCollectionGetReq();
        carParkingTollCollectionGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        CarParkingTollCollectionResp resp = carParkingTollCollectionService
                .getCarParkingTollCollection(carParkingTollCollectionGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<CarParkingTollCollectionResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.CAR_PARKING_TOLL_COLLECTION_DELETE, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> carParkingTollCollectionDelete(
            @RequestBody CarParkingTollCollectionDeleteReq carParkingTollCollectionDeleteReq) {
        carParkingTollCollectionService.carParkingTollCollectionDelete(carParkingTollCollectionDeleteReq);

        CarParkingTollCollectionGetReq carParkingTollCollectionGetReq = new CarParkingTollCollectionGetReq();
        carParkingTollCollectionGetReq.setId(null);
        carParkingTollCollectionGetReq.setStatus(StatusCodes.ACTIVE.getValue());
        CarParkingTollCollectionResp resp = carParkingTollCollectionService
                .getCarParkingTollCollection(carParkingTollCollectionGetReq);
        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<CarParkingTollCollectionResp>(resp, HttpStatus.OK);
    }
    

    @GetMapping(value = RegisterURLConstants.CAR_PARKING_TOLL_COLLECTION_DOC_DOWNLOAD_FILE)
    public ResponseEntity<ByteArrayResource> carParkingTollCollectionDocDownloadFile(@RequestParam("fileId") Long fileId) {
        CarParkingTollCollectionDtlTO fileTo = carParkingTollCollectionService
                .carParkingTollCollectionDocDownloadFile(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileTo.getCarParkingTollFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileTo.getCarParkingTollFileName() + "\"")
                .body(new ByteArrayResource(fileTo.getCarParkingTollDocuments()));
    }    
    
    @RequestMapping(value = RegisterURLConstants.GET_FIXED_ASSETS_CAR_PARKING_DOCUMENTS, method = RequestMethod.POST)
    public ResponseEntity<DocumentsResp> getProjCarParkingDocuemnts(
            @RequestBody CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        DocumentsResp resp = carParkingTollCollectionService
                .getProjCarParkingDocuemnts(carParkingTollCollectionGetReq);
        return new ResponseEntity<DocumentsResp>(resp, HttpStatus.OK);
    }
}
