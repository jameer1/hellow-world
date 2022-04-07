package com.rjtech.register.service.impl.fixedassets;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.MortgagePaymentDtlTO;
import com.rjtech.register.fixedassets.model.MortgagePaymentDtlEntity;
import com.rjtech.register.fixedassets.req.MortgageePaymentsDeactiveReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsGetReq;
import com.rjtech.register.fixedassets.req.MortgageePaymentsSaveReq;
import com.rjtech.register.fixedassets.resp.MortgagePaymentResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.MortgagePaymentRepository;
import com.rjtech.register.service.fixedassets.MortgagePaymentService;
import com.rjtech.register.service.handler.fixedassets.MortgagePaymentDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "mortgagePaymentService")
@RJSService(modulecode = "mortgagePaymentService")
@Transactional
public class MortgagePaymentServiceImpl implements MortgagePaymentService {

    @Autowired
    private MortgagePaymentRepository mortgagePaymentRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    public MortgagePaymentResp getMortgageePayments(MortgageePaymentsGetReq mortgageePaymentsGetReq) {

        MortgagePaymentResp resp = new MortgagePaymentResp();
        List<MortgagePaymentDtlEntity> mortgagePaymentDtlEntities = mortgagePaymentRepository.findAllMortgageePayments(
                mortgageePaymentsGetReq.getFixedAssetid(), mortgageePaymentsGetReq.getStatus());
        for (MortgagePaymentDtlEntity mortgagePaymentDtlEntity : mortgagePaymentDtlEntities) {
            resp.getMortgageValueDtlTOs().add(MortgagePaymentDtlHandler.convertEntityToPOJO(mortgagePaymentDtlEntity));
        }
        return resp;
    }

    public void saveMortgageePayments(MortgageePaymentsSaveReq mortgageePaymentsSaveReq) {

    	int count = 0;
        List<MortgagePaymentDtlEntity> list = new ArrayList<MortgagePaymentDtlEntity>();
        if (CommonUtil.isListHasData(mortgageePaymentsSaveReq.getMortgageValueDtlTOs())) {
            for (MortgagePaymentDtlTO mortgagePaymentDtlTO : mortgageePaymentsSaveReq.getMortgageValueDtlTOs()) {
                MortgagePaymentDtlEntity entity = null;
                MortgagePaymentDtlEntity mortgagePaymentDtlEntity;
                
                count = mortgagePaymentRepository.getCountOfMortgagePayment(mortgagePaymentDtlTO.getFixedAssetid());
                
                if (CommonUtil.isNonBlankLong(mortgagePaymentDtlTO.getId())) {
                    entity = mortgagePaymentRepository.findOne(mortgagePaymentDtlTO.getId());
                } else {
                    entity = new MortgagePaymentDtlEntity();
                }
                mortgagePaymentDtlEntity = MortgagePaymentDtlHandler.convertPOJOToEntity(entity, mortgagePaymentDtlTO,
                        fixedAssetsRegisterRepository);
                
                List<MortgagePaymentDtlEntity> list123 = mortgagePaymentRepository.findAllMortgageePayments(mortgagePaymentDtlTO.getFixedAssetid(), 1);
                for(MortgagePaymentDtlEntity mortgagePaymentDtlEntites : list123) {
                	for(int i=0;i<count;i++) {
                		mortgagePaymentDtlEntites.setAccountStatus("Inactive");
                	}
                }	
                list.add(mortgagePaymentDtlEntity);
            }
            mortgagePaymentRepository.save(list);
        }
    }

    public void deactivateMortgageePayments(MortgageePaymentsDeactiveReq mortgageePaymentsDeactiveReq) {
        mortgagePaymentRepository.deactivateSubAsset(mortgageePaymentsDeactiveReq.getMortagageePaymentsIds(),mortgageePaymentsDeactiveReq.getStatus());
    }

    public void mortgageePaymentsDelete(MortgageePaymentsDeactiveReq mortgageePaymentsDeleteReq) {
        mortgagePaymentRepository.mortgageePaymentsDelete(mortgageePaymentsDeleteReq.getMortagageePaymentsIds());
    }
}
