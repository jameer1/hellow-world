package com.rjtech.register.service.fixedassets;

import com.rjtech.register.fixedassets.req.MortgageePaymentsDeactiveReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsGetReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsSaveReq;
import com.rjtech.register.fixedassets.resp.MortgagePaymentResp;

public interface MortgagePaymentService {

    MortgagePaymentResp getMortgageePayments(MortgageePaymentsGetReq mortgageePaymentsGetReq);

    void saveMortgageePayments(MortgageePaymentsSaveReq mortgageePaymentsSaveReq);

    void deactivateMortgageePayments(MortgageePaymentsDeactiveReq mortgageePaymentsDeactiveReq);

    void mortgageePaymentsDelete(MortgageePaymentsDeactiveReq mortgageePaymentsDeleteReq);

}
