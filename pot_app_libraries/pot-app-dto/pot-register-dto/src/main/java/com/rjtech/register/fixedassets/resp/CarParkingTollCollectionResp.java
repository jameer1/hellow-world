package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.CarParkingTollCollectionDtlTO;

public class CarParkingTollCollectionResp extends AppResp {

    private static final long serialVersionUID = 1L;
    private List<CarParkingTollCollectionDtlTO> carParkingTollCollectionDtlTOs = new ArrayList<CarParkingTollCollectionDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<CarParkingTollCollectionDtlTO> getCarParkingTollCollectionDtlTOs() {
        return carParkingTollCollectionDtlTOs;
    }

    public void setCarParkingTollCollectionDtlTOs(List<CarParkingTollCollectionDtlTO> carParkingTollCollectionDtlTOs) {
        this.carParkingTollCollectionDtlTOs = carParkingTollCollectionDtlTOs;
    }

}
