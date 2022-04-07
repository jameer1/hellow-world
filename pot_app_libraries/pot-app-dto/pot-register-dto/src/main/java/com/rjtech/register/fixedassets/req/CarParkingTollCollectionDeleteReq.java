package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.CarParkingTollCollectionDtlTO;

public class CarParkingTollCollectionDeleteReq extends CarParkingTollCollectionDtlTO {

    private static final long serialVersionUID = -4543819922890869538L;
    List<Long> carParkingTollCollectionIds = new ArrayList<Long>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<Long> getCarParkingTollCollectionIds() {
        return carParkingTollCollectionIds;
    }

    public void setCarParkingTollCollectionIds(List<Long> carParkingTollCollectionIds) {
        this.carParkingTollCollectionIds = carParkingTollCollectionIds;
    }

}
