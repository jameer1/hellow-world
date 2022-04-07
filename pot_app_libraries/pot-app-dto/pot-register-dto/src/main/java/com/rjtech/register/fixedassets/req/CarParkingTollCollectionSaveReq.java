package com.rjtech.register.fixedassets.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.constants.ApplicationConstants;
import com.rjtech.register.fixedassets.dto.CarParkingTollCollectionDtlTO;

public class CarParkingTollCollectionSaveReq extends CarParkingTollCollectionDtlTO {
    private static final long serialVersionUID = 2740448397989044793L;
    private String folderCategory;
    private Long userId;
    
    private List<CarParkingTollCollectionDtlTO> carParkingTollCollectionDtlTO = new ArrayList<CarParkingTollCollectionDtlTO>(
            ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    public List<CarParkingTollCollectionDtlTO> getCarParkingTollCollectionDtlTO() {
        return carParkingTollCollectionDtlTO;
    }

    public void setCarParkingTollCollectionDtlTO(List<CarParkingTollCollectionDtlTO> carParkingTollCollectionDtlTO) {
        this.carParkingTollCollectionDtlTO = carParkingTollCollectionDtlTO;
    }
    
    public void setFolderCategory( String folderCategory ) {
    	this.folderCategory = folderCategory;
    }

    public String getFolderCategory() {
    	return folderCategory;
    }
    
    public void setUserId( Long userId ) {
    	this.userId = userId;
    }

    public Long getUserId() {
    	return userId;
    }
    
    public String toString()
    {
    	return "from CarParkingTollCollectionSaveReq folderCategory:"+folderCategory+" userId:"+userId;
    }
}
