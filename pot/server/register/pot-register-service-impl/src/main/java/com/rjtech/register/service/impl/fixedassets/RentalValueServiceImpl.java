package com.rjtech.register.service.impl.fixedassets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.register.fixedassets.dto.RentalValueDtlTO;
import com.rjtech.register.fixedassets.model.RentalValueDtlEntity;
import com.rjtech.register.fixedassets.req.RentalValueDeactiveReq;
import com.rjtech.register.fixedassets.req.RentalValueGetReq;
import com.rjtech.register.fixedassets.req.RentalValueSaveReq;
import com.rjtech.register.fixedassets.resp.RentalValueResp;
import com.rjtech.register.repository.fixeassets.FixedAssetsRegisterRepository;
import com.rjtech.register.repository.fixeassets.RentalValueRepository;
import com.rjtech.register.service.fixedassets.RentalValueService;
import com.rjtech.register.service.handler.fixedassets.RentalValueDtlHandler;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "rentalValueService")
@RJSService(modulecode = "rentalValueService")
@Transactional
public class RentalValueServiceImpl implements RentalValueService {

    @Autowired
    private RentalValueRepository rentalValueRepository;

    @Autowired
    private FixedAssetsRegisterRepository fixedAssetsRegisterRepository;

    public RentalValueResp getRentalValue(RentalValueGetReq rentalValueGetReq) {

        RentalValueResp resp = new RentalValueResp();
        List<RentalValueDtlEntity> rentalValueDtlEntities = rentalValueRepository
                .findAllRentalValue(rentalValueGetReq.getFixedAssetid(), rentalValueGetReq.getStatus());
        for (RentalValueDtlEntity rentalValueDtlEntity : rentalValueDtlEntities) {
            resp.getRentalValueDtlTOs().add(RentalValueDtlHandler.convertEntityToPOJO(rentalValueDtlEntity));
        }
        return resp;
    }

    public void saveRentalValue(RentalValueSaveReq rentalValueSaveReq) {

        List<RentalValueDtlEntity> list = new ArrayList<RentalValueDtlEntity>();

        if (CommonUtil.isListHasData(rentalValueSaveReq.getRentalValueDtlTOs())) {
            for (RentalValueDtlTO rentalValueDtlTO : rentalValueSaveReq.getRentalValueDtlTOs()) {
                RentalValueDtlEntity entity = null;
                RentalValueDtlEntity rentalValueDtlEntity;

                if (CommonUtil.isNonBlankLong(rentalValueDtlTO.getId())) {
                    entity = rentalValueRepository.findOne(rentalValueDtlTO.getId());
                } else {
                    entity = new RentalValueDtlEntity();
                }
                rentalValueDtlEntity = RentalValueDtlHandler.convertPOJOToEntity(entity, rentalValueDtlTO,
                        fixedAssetsRegisterRepository);
                list.add(rentalValueDtlEntity);
            }
            rentalValueRepository.save(list);
        }

    }

    public void deactivateRentalValue(RentalValueDeactiveReq rentalValueDeactiveReq) {
        rentalValueRepository.deactivateRentalValue(rentalValueDeactiveReq.getRentalValueIds(),
                rentalValueDeactiveReq.getStatus());

    }

    public void rentalValueDelete(RentalValueDeactiveReq rentalValueDeleteReq) {
        rentalValueRepository.rentalValueDelete(rentalValueDeleteReq.getRentalValueIds());

    }

}
