package com.rjtech.register.service.impl.fixedassets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.eps.service.handler.EPSProjServiceHandler;
import com.rjtech.projectlib.resp.ProjectResp;
import com.rjtech.register.fixedassets.dto.SubAssetDtlTO;
import com.rjtech.register.fixedassets.model.SubAssetDtlEntity;
import com.rjtech.register.fixedassets.req.SubAssetDeleteReq;
import com.rjtech.register.fixedassets.req.SubAssetsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.SubAssetsResp;
import com.rjtech.register.repository.fixeassets.SubAssetsRepository;
import com.rjtech.register.service.fixedassets.SubAssetsService;
import com.rjtech.register.service.handler.fixedassets.SubAssetsDtlHandler;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "subAssetsService")
@RJSService(modulecode = "subAssetsService")
@Transactional
public class SubAssetServiceImp implements SubAssetsService {

    @Autowired
    private SubAssetsRepository subAssetsRepository;

    public SubAssetsResp getSubAssets(SubAssetsGetReq subAssetsGetReq) {
        SubAssetsResp resp = new SubAssetsResp();
        List<SubAssetDtlEntity> subAssetDtlEntities = subAssetsRepository
                .findAllSubAsset(subAssetsGetReq.getFixedAssetId(), subAssetsGetReq.getStatus());
        for (SubAssetDtlEntity subAssetDtlEntity : subAssetDtlEntities) {
            resp.getSubAssetDtlTOs().add(SubAssetsDtlHandler.convertEntityToPOJO(subAssetDtlEntity));
        }

        return resp;
    }

    public void saveSubAssets(SubAssetsSaveReq subAssetsSaveReq) {

        List<SubAssetDtlEntity> list = new ArrayList<SubAssetDtlEntity>();

        if (CommonUtil.isListHasData(subAssetsSaveReq.getSubAssetDtlTO())) {
            for (SubAssetDtlTO subAssetDtlTO : subAssetsSaveReq.getSubAssetDtlTO()) {
                SubAssetDtlEntity entity = null;

                if (CommonUtil.isNonBlankLong(subAssetDtlTO.getSubid())) {
                    entity = subAssetsRepository.findOne(subAssetDtlTO.getSubid());
                } else {
                    entity = new SubAssetDtlEntity();
                }

                SubAssetDtlEntity subAssetDtlEntity = SubAssetsDtlHandler.convertPOJOToEntity(entity, subAssetDtlTO);
                list.add(subAssetDtlEntity);
            }

        }
        subAssetsRepository.save(list);

    }

    public void deactivateSubAsset(SubAssetDeleteReq subAssetDeleteReq) {
        subAssetsRepository.deactivateSubAsset(subAssetDeleteReq.getSubAssetIds(), subAssetDeleteReq.getStatus());
    }

    public void subAssetDelete(SubAssetDeleteReq subAssetDeleteReq) {
        subAssetsRepository.subAssetDelete(subAssetDeleteReq.getSubAssetIds());

    }

    public SubAssetsResp getAssetById(SubAssetsGetReq subAssetsGetReq) {
        List<SubAssetDtlEntity> subAssetDtlEntities = subAssetsRepository
                .findAssetById(subAssetsGetReq.getFixedAssetId(), subAssetsGetReq.getStatus());
        SubAssetsResp subAssetsResp = new SubAssetsResp();
        for (SubAssetDtlEntity subAssetDtlEntity : subAssetDtlEntities) {
            subAssetsResp.getSubAssetDtlTOs().add(SubAssetsDtlHandler.convertEntityToPOJO(subAssetDtlEntity));
        }
        return subAssetsResp;
    }

}
