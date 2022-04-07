package com.rjtech.register.service.fixedassets;

import com.rjtech.register.fixedassets.req.RevenueSalesDeactiveReq;
import com.rjtech.register.fixedassets.req.RevenueSalesGetReq;
import com.rjtech.register.fixedassets.req.RevenueSalesSaveReq;
import com.rjtech.register.fixedassets.resp.RevenueSalesResp;

public interface RevenueSalesService {

    RevenueSalesResp getRevenueSales(RevenueSalesGetReq revenueSalesGetReq);

    void saveRevenueSales(RevenueSalesSaveReq revenueSalesSaveReq);

    void deactivateRevenueSales(RevenueSalesDeactiveReq revenueSalesDeactiveReq);

    void revenueSalesDelete(RevenueSalesDeactiveReq revenueSalesDeleteReq);

}
