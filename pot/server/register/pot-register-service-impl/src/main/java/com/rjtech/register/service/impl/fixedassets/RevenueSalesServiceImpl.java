package com.rjtech.register.service.impl.fixedassets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.RevenueSalesDtlTO;
import com.rjtech.register.fixedassets.model.RevenueSalesDtlEntity;
import com.rjtech.register.fixedassets.req.RevenueSalesDeactiveReq;
import com.rjtech.register.fixedassets.req.RevenueSalesGetReq;
import com.rjtech.register.fixedassets.req.RevenueSalesSaveReq;
import com.rjtech.register.fixedassets.resp.RevenueSalesResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.RevenueSalesRepository;
import com.rjtech.register.service.fixedassets.RevenueSalesService;
import com.rjtech.register.service.handler.fixedassets.RevenueSalesDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "revenueSalesService")
@RJSService(modulecode = "revenueSalesService")
@Transactional
public class RevenueSalesServiceImpl implements RevenueSalesService {

    @Autowired
    private RevenueSalesRepository revenueSalesRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    public RevenueSalesResp getRevenueSales(RevenueSalesGetReq revenueSalesGetReq) {
        RevenueSalesResp resp = new RevenueSalesResp();
        List<RevenueSalesDtlEntity> revenueSalesDtlEntities = revenueSalesRepository
                .findAllRevenueSales(revenueSalesGetReq.getFixedAssetid(), revenueSalesGetReq.getStatus());
        for (RevenueSalesDtlEntity revenueSalesDtlEntity : revenueSalesDtlEntities) {
            resp.getRevenueSalesDtlTOs().add(RevenueSalesDtlHandler.convertEntityToPOJO(revenueSalesDtlEntity));
        }
        return resp;
    }

    public void saveRevenueSales(RevenueSalesSaveReq revenueSalesSaveReq) {

        List<RevenueSalesDtlEntity> revenueSalesDtlEntities = new ArrayList<RevenueSalesDtlEntity>();

        if (CommonUtil.isListHasData(revenueSalesSaveReq.getRevenueSalesDtlTOs())) {
            for (RevenueSalesDtlTO revenueSalesDtlTO : revenueSalesSaveReq.getRevenueSalesDtlTOs()) {
                RevenueSalesDtlEntity entity = null;

                if (CommonUtil.isNonBlankLong(revenueSalesDtlTO.getId())) {
                    entity = revenueSalesRepository.findOne(revenueSalesDtlTO.getId());
                } else {
                    entity = new RevenueSalesDtlEntity();
                }

                RevenueSalesDtlEntity revenueSalesDtlEntity = RevenueSalesDtlHandler.convertPOJOToEntity(entity,
                        revenueSalesDtlTO, fixedAssetsRegisterRepository);
                revenueSalesDtlEntities.add(revenueSalesDtlEntity);
            }
        }
        revenueSalesRepository.save(revenueSalesDtlEntities);

    }

    public void deactivateRevenueSales(RevenueSalesDeactiveReq revenueSalesDeactiveReq) {

        revenueSalesRepository.deactivateRevenueSales(revenueSalesDeactiveReq.getRevenueSalesIds(),
                revenueSalesDeactiveReq.getStatus());

    }

    public void revenueSalesDelete(RevenueSalesDeactiveReq revenueSalesDeleteReq) {
        revenueSalesRepository.revenueSalesDelete(revenueSalesDeleteReq.getRevenueSalesIds());

    }

}
