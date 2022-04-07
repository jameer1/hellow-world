package com.rjtech.register.service.fixedassets;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.fixedassets.dto.CarParkingTollCollectionDtlTO;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionDeleteReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionGetReq;
import com.rjtech.register.fixedassets.req.CarParkingTollCollectionSaveReq;
import com.rjtech.register.fixedassets.resp.CarParkingTollCollectionResp;
import com.rjtech.register.fixedassets.resp.DocumentsResp;

public interface CarParkingTollCollectionService {

    CarParkingTollCollectionResp getCarParkingTollCollection(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq);
    
    
    CarParkingTollCollectionResp getCarParkingTollCollectionLastRecord(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq);

    void saveCarParkingTollCollection(MultipartFile file, CarParkingTollCollectionSaveReq carParkingTollCollectionSave);

    void carParkingTollCollectionDelete(CarParkingTollCollectionDeleteReq carParkingTollCollectionDeleteReq);

    CarParkingTollCollectionDtlTO carParkingTollCollectionDocDownloadFile(Long fileId);
    
    DocumentsResp getProjCarParkingDocuemnts(
            CarParkingTollCollectionGetReq carParkingTollCollectionGetReq);

}
