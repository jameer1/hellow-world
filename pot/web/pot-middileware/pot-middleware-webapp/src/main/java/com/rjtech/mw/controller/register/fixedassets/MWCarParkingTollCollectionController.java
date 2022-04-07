package com.rjtech.mw.controller.register.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rjtech.mw.service.register.MWFixedAssetsService;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionDeleteReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionGetReq;
import com.rjtech.register.fixedassets.resp.CarParkingTollCollectionResp;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MWCarParkingTollCollectionController {

    @Autowired
    private MWFixedAssetsService mwFixedAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_CAR_PARKING_TOLL_COLLECTION, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> getCarParkingTollCollection(
            @RequestBody CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        CarParkingTollCollectionResp resp = mwFixedAssetsService
                .getCarParkingTollCollection(carParkingTollCollectionGetReq);
        return new ResponseEntity<CarParkingTollCollectionResp>(resp, HttpStatus.OK);
    }
    
    // last record in method
    
    @RequestMapping(value = RegisterURLConstants.GET_CAR_PARKING_TOLL_COLLECTION_LAST_RECORD, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> getCarParkingTollCollectionLastRecord(
            @RequestBody CarParkingTollCollectionGetReq carParkingTollCollectionGetReq) {
        CarParkingTollCollectionResp resp = mwFixedAssetsService
                .getCarParkingTollCollectionLastRecord(carParkingTollCollectionGetReq);
        return new ResponseEntity<CarParkingTollCollectionResp>(resp, HttpStatus.OK);
    }
    
    //====================
    
    @RequestMapping(value = RegisterURLConstants.SAVE_CAR_PARKING_TOLL_COLLECTION, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> saveCarParkingTollCollection(
            @RequestBody MultipartFile file, String carParkingTollCollectionSaveReq) {
        return new ResponseEntity<CarParkingTollCollectionResp>(
                mwFixedAssetsService.saveCarParkingTollCollection(file, carParkingTollCollectionSaveReq),
                HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.CAR_PARKING_TOLL_COLLECTION_DELETE, method = RequestMethod.POST)
    public ResponseEntity<CarParkingTollCollectionResp> carParkingTollCollectionDelete(
            @RequestBody CarParkingTollCollectionDeleteReq carParkingTollCollectionDeleteReq) {
        return new ResponseEntity<CarParkingTollCollectionResp>(
                mwFixedAssetsService.carParkingTollCollectionDelete(carParkingTollCollectionDeleteReq), HttpStatus.OK);
    }

    @GetMapping(value = RegisterURLConstants.CAR_PARKING_TOLL_COLLECTION_DOC_DOWNLOAD_FILE)
    public ResponseEntity<Void> carParkingTollCollectionDocDownloadFile(@RequestParam("fileId") Long fileId) {
        mwFixedAssetsService.carParkingTollCollectionDocDownloadFile(fileId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

    }

}
