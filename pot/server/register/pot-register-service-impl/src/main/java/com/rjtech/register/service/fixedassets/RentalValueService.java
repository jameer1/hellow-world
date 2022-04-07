package com.rjtech.register.service.fixedassets;

import com.rjtech.register.fixedassets.req.RentalValueDeactiveReq;
import com.rjtech.register.fixedassets.req.RentalValueGetReq;
import com.rjtech.register.fixedassets.req.RentalValueSaveReq;
import com.rjtech.register.fixedassets.resp.RentalValueResp;

public interface RentalValueService {

    RentalValueResp getRentalValue(RentalValueGetReq rentalValueGetReq);

    void saveRentalValue(RentalValueSaveReq rentalValueSaveReq);

    void deactivateRentalValue(RentalValueDeactiveReq rentalValueDeactiveReq);

    void rentalValueDelete(RentalValueDeactiveReq rentalValueDeleteReq);

}
