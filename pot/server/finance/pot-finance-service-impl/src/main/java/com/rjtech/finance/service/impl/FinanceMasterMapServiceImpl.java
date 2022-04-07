package com.rjtech.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.req.UserReq;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.finance.model.TaxCodesEntity;
import com.rjtech.finance.repository.TaxCodesRepository;
import com.rjtech.finance.resp.FinanceMapResp;
import com.rjtech.finance.service.FinanceMasterMapService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "financeMasterMapService")
@RJSService(modulecode = "financeMasterMapService")
@Transactional

public class FinanceMasterMapServiceImpl implements FinanceMasterMapService {

    @Autowired
    private TaxCodesRepository taxCodesRepository;

    public FinanceMapResp taxCodeMap(UserReq userReq) {
        FinanceMapResp taxCodeMapResp = new FinanceMapResp();
        List<TaxCodesEntity> taxCodesEntities = taxCodesRepository.findAllTaxCodes(AppUserUtils.getClientId());

        if (CommonUtil.isListHasData(taxCodesEntities)) {

            for (TaxCodesEntity taxCodesEntity : taxCodesEntities) {

                if (CommonUtil.isNotBlankStr(taxCodesEntity.getCode())) {

                    taxCodeMapResp.getUniqueCodeMap().put(taxCodesEntity.getCode().toUpperCase().trim(),
                            taxCodesEntity.getStatus());

                }
            }
        }
        return taxCodeMapResp;
    }

}
