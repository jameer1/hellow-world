package com.rjtech.projectlib.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.centrallib.model.EmpClassMstrEntity;
import com.rjtech.centrallib.model.PlantMstrEntity;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.LabelKeyTOMapResp;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.model.ProjCostItemEntity;
import com.rjtech.projectlib.model.ProjCrewMstrEntity;
import com.rjtech.projectlib.model.ProjEmpClassMstrEntity;
import com.rjtech.projectlib.model.ProjPlantClassMstrEntity;
import com.rjtech.projectlib.model.ProjSOEItemEntity;
import com.rjtech.projectlib.model.ProjSORItemEntity;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
import com.rjtech.projectlib.model.ProjWorkShiftMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjCostItemRepository;
import com.rjtech.projectlib.repository.ProjCrewRepository;
import com.rjtech.projectlib.repository.ProjEmpClassRepository;
import com.rjtech.projectlib.repository.ProjPlantClassRepository;
import com.rjtech.projectlib.repository.ProjProcRepository;
import com.rjtech.projectlib.repository.ProjSOEItemRepository;
import com.rjtech.projectlib.repository.ProjSORItemRepositoryCopy;
import com.rjtech.projectlib.repository.ProjStoreStockRepository;
import com.rjtech.projectlib.repository.ProjWorkShiftRepository;
import com.rjtech.projectlib.req.ProjCrewGetReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.resp.ProjLibMapResp;
import com.rjtech.projectlib.resp.ProjLibUniqueMapResp;
import com.rjtech.projectlib.service.ProjLibMapService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "projLibMapService")
@RJSService(modulecode = "projLibMapService")
@Transactional
public class ProjLibMapServiceImpl implements ProjLibMapService {

    @Autowired
    private ProjEmpClassRepository projEmpClassRepository;

    @Autowired
    private ProjStoreStockRepository projStoreStockRepository;

    @Autowired
    private ProjWorkShiftRepository projWorkShiftRepository;

    @Autowired
    private ProjCrewRepository projCrewRepository;

    @Autowired
    private ProjSOEItemRepository projSOEItemRepository;

    @Autowired
    private ProjSORItemRepositoryCopy projSORItemRepository;

    @Autowired
    private ProjCostItemRepository projCostItemRepository;

    @Autowired
    private EPSProjRepository ePSProjRepository;

    @Autowired
    private ProjPlantClassRepository projPlantClassRepository;

    @Autowired
    private ProjProcRepository projProcRepository;

    public ProjLibMapResp ProjStockPileMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibMapResp projStockPileMapResp = new ProjLibMapResp();
        List<ProjStoreStockMstrEntity> projStoreStockMstrEntities = projStoreStockRepository
                .findAllProjStoreStocks((projCrewGetReq.getProjId()));
        if (CommonUtil.isListHasData(projStoreStockMstrEntities)) {
            for (ProjStoreStockMstrEntity projStoreStockMstrEntity : projStoreStockMstrEntities) {
                if (CommonUtil.isNotBlankStr(projStoreStockMstrEntity.getCode())) {
                    projStockPileMapResp.getProjUniqueCodeMap().put(
                            projStoreStockMstrEntity.getCode().toUpperCase().trim(),
                            projStoreStockMstrEntity.getStatus());
                }
            }
        }
        return projStockPileMapResp;
    }

    public ProjLibMapResp ProjEmpClassMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibMapResp projEmpClassMapResp = new ProjLibMapResp();
        List<ProjEmpClassMstrEntity> projEmpClassMstrEntities = projEmpClassRepository
                .findAllProjEmpClasses((projCrewGetReq.getProjId()));
        Long empClassId = null;
        String projEmpCategory = null;
        String code = null;
        EmpClassMstrEntity empClassMstr = null;
        if (CommonUtil.isListHasData(projEmpClassMstrEntities)) {
            for (ProjEmpClassMstrEntity projEmpClassMstrEntity : projEmpClassMstrEntities) {

                empClassMstr = projEmpClassMstrEntity.getEmpClassMstrEntity();
                if (empClassMstr != null)
                    empClassId = empClassMstr.getId();
                projEmpCategory = projEmpClassMstrEntity.getProjEmpCategory();

                code = CommonUtil.concatCodeName(empClassId.toString(), projEmpCategory);

                if (CommonUtil.isNotBlankStr(code)) {
                    projEmpClassMapResp.getProjUniqueCodeMap().put(code.toUpperCase().trim(),
                            projEmpClassMstrEntity.getStatus());

                }
            }
        }
        return projEmpClassMapResp;

    }

    public ProjLibMapResp ProjWorkShiftMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibMapResp projWorkShiftMapResp = new ProjLibMapResp();
        List<ProjWorkShiftMstrEntity> projWorkShiftMstrEntities = projWorkShiftRepository
                .findAllProjWorkShifts((projCrewGetReq.getProjId()));
        if (CommonUtil.isListHasData(projWorkShiftMstrEntities)) {
            for (ProjWorkShiftMstrEntity projWorkShiftMstrEntity : projWorkShiftMstrEntities) {
                if (CommonUtil.isNotBlankStr(projWorkShiftMstrEntity.getCode())) {
                    projWorkShiftMapResp.getProjUniqueCodeMap().put(
                            projWorkShiftMstrEntity.getCode().toUpperCase().trim(),
                            projWorkShiftMstrEntity.getStatus());
                }
            }
        }
        return projWorkShiftMapResp;
    }

    public ProjLibMapResp ProjCrewListMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibMapResp projCrewListMapResp = new ProjLibMapResp();
        List<ProjCrewMstrEntity> projCrewMstrEntities = projCrewRepository
                .findAllProjCrewLists((projCrewGetReq.getProjId()));
        if (CommonUtil.isListHasData(projCrewMstrEntities)) {
            for (ProjCrewMstrEntity projCrewMstrEntity : projCrewMstrEntities) {
                if (CommonUtil.isNotBlankStr(projCrewMstrEntity.getCode())) {
                    projCrewListMapResp.getProjUniqueCodeMap().put(projCrewMstrEntity.getCode().toUpperCase().trim(),
                            projCrewMstrEntity.getStatus());
                }
            }
        }
        return projCrewListMapResp;
    }

    public ProjLibMapResp ProjSOEMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibMapResp projSOEMapResp = new ProjLibMapResp();
        List<ProjSOEItemEntity> projSOEItemEntities = projSOEItemRepository
                .findAllSOEDetails((projCrewGetReq.getProjId()));
        if (CommonUtil.isListHasData(projSOEItemEntities)) {
            for (ProjSOEItemEntity projSOEItemEntity : projSOEItemEntities) {
                if (CommonUtil.isNotBlankStr(projSOEItemEntity.getCode())) {
                    projSOEMapResp.getProjUniqueCodeMap().put(projSOEItemEntity.getCode().toUpperCase().trim(),
                            projSOEItemEntity.getStatus());
                }
            }
        }
        return projSOEMapResp;
    }

    public ProjLibMapResp ProjSORMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibMapResp projSORMapResp = new ProjLibMapResp();
        List<ProjSORItemEntity> projSORItemEntities = projSORItemRepository
                .findAllSORDetails((projCrewGetReq.getProjId()));
        if (CommonUtil.isListHasData(projSORItemEntities)) {
            for (ProjSORItemEntity projSORItemEntity : projSORItemEntities) {
                if (CommonUtil.isNotBlankStr(projSORItemEntity.getCode())) {
                    projSORMapResp.getProjUniqueCodeMap().put(projSORItemEntity.getCode().toUpperCase().trim(),
                            projSORItemEntity.getStatus());
                }
            }
        }
        return projSORMapResp;
    }

    public ProjLibMapResp ProjCostCodeMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibMapResp projCostCodeMapResp = new ProjLibMapResp();
        List<ProjCostItemEntity> projCostItemEntities = projCostItemRepository
                .findAllCostCodeItems((projCrewGetReq.getProjId()));
        if (CommonUtil.isListHasData(projCostItemEntities)) {
            for (ProjCostItemEntity projCostItemEntity : projCostItemEntities) {
                if (CommonUtil.isNotBlankStr(projCostItemEntity.getCode())) {
                    projCostCodeMapResp.getProjUniqueCodeMap().put(projCostItemEntity.getCode().toUpperCase().trim(),
                            projCostItemEntity.getStatus());
                }
            }
        }
        return projCostCodeMapResp;
    }

    public ProjLibMapResp getEpsListMap(ProjGetReq projGetReq) {
        ProjLibMapResp projEpsMapResp = new ProjLibMapResp();
        List<ProjMstrEntity> projMstrEntities = ePSProjRepository.findProjectsByClientId(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(projMstrEntities)) {
            for (ProjMstrEntity projMstrEntity : projMstrEntities) {
                if (projMstrEntity.isProj()) {
                    if (CommonUtil.isNotBlankStr(projMstrEntity.getCode())) {
                        projEpsMapResp.getProjUniqueCodeMap().put(projMstrEntity.getCode().toUpperCase().trim(),
                                projMstrEntity.getStatus());
                    }
                }
            }
        }
        return projEpsMapResp;
    }

    public ProjLibUniqueMapResp ProjPlantClassMap(ProjCrewGetReq projCrewGetReq) {
        ProjLibUniqueMapResp uniqueMapResp = new ProjLibUniqueMapResp();
        List<ProjPlantClassMstrEntity> projPlantClassMstrEntities = projPlantClassRepository
                .findAllProjPlantClasses((projCrewGetReq.getProjId()));
        if (CommonUtil.isListHasData(projPlantClassMstrEntities)) {
            PlantMstrEntity plantMstr = null;
            for (ProjPlantClassMstrEntity projPlantClassMstrEntity : projPlantClassMstrEntities) {
                plantMstr = projPlantClassMstrEntity.getPlantMstrEntity();
                if (CommonUtil.objectNotNull(plantMstr)) {
                    uniqueMapResp.getProjUniqueCodeMap().put(plantMstr.getId(), projPlantClassMstrEntity.getStatus());
                }
            }
        }
        return uniqueMapResp;
    }

    public ProjLibMapResp getEpsProjectMap(ProjGetReq projGetReq) {
        ProjLibMapResp projEpsMapResp = new ProjLibMapResp();
        List<ProjMstrEntity> projMstrEntities = ePSProjRepository.findAllProjects();
        if (CommonUtil.isListHasData(projMstrEntities)) {
            for (ProjMstrEntity projMstrEntity : projMstrEntities) {
                if (projMstrEntity.isProj()) {
                    ProjMstrEntity parentProjMstrEntity = projMstrEntity.getParentProjectMstrEntity();
                    if ((CommonUtil.isNotBlankStr(projMstrEntity.getCode())
                            && (CommonUtil.objectNotNull(parentProjMstrEntity)))) {
                        projEpsMapResp.getProjUniqueCodeMap().put(
                                projMstrEntity.getCode().toUpperCase().trim() + " " + parentProjMstrEntity,
                                projMstrEntity.getStatus());
                    }
                }
            }
        }
        return projEpsMapResp;
    }

    public LabelKeyTOMapResp getMultiProjCodeMap(ProjGetReq projGetReq) {
        LabelKeyTOMapResp labelKeyTOMapResp = new LabelKeyTOMapResp();
        List<LabelKeyTO> labelKeyTOs = projProcRepository.getProjCodes(StringUtils.join(projGetReq.getProjIds(), ','));
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            for (LabelKeyTO labelKeyTO : labelKeyTOs) {
                labelKeyTOMapResp.getLabelKeyTOList().put(labelKeyTO.getId(), labelKeyTO);
            }
        }
        return labelKeyTOMapResp;
    }

    public LabelKeyTOMapResp getMultiEpsCodeMap(ProjGetReq projGetReq) {
        LabelKeyTOMapResp labelKeyTOMapResp = new LabelKeyTOMapResp();
        List<LabelKeyTO> labelKeyTOs = projProcRepository.getEPSProjCodes(AppUserUtils.getClientId());
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            for (LabelKeyTO labelKeyTO : labelKeyTOs) {
                labelKeyTOMapResp.getLabelKeyTOList().put(labelKeyTO.getId(), labelKeyTO);
            }
        }
        return labelKeyTOMapResp;
    }

    public LabelKeyTOMapResp getMultiProjSOWItemMap(ProjGetReq projGetReq) {
        LabelKeyTOMapResp labelKeyTOMapResp = new LabelKeyTOMapResp();
        List<LabelKeyTO> labelKeyTOs = projProcRepository
                .getProjSOWItems(StringUtils.join(Arrays.asList(projGetReq.getProjIds()), ','));
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            for (LabelKeyTO labelKeyTO : labelKeyTOs) {
                labelKeyTOMapResp.getLabelKeyTOList().put(labelKeyTO.getId(), labelKeyTO);
            }
        }
        return labelKeyTOMapResp;
    }

    public LabelKeyTOMapResp getMultiProjSORItemMap(ProjGetReq projGetReq) {
        LabelKeyTOMapResp labelKeyTOMapResp = new LabelKeyTOMapResp();
        List<LabelKeyTO> labelKeyTOs = projProcRepository
                .getProjSORItems(StringUtils.join(projGetReq.getProjIds(), ','));
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            for (LabelKeyTO labelKeyTO : labelKeyTOs) {
                labelKeyTOMapResp.getLabelKeyTOList().put(labelKeyTO.getId(), labelKeyTO);
            }
        }
        return labelKeyTOMapResp;
    }

    public LabelKeyTOMapResp getMultiProjCostCodeMap(ProjGetReq projGetReq) {
        LabelKeyTOMapResp labelKeyTOMapResp = new LabelKeyTOMapResp();
        List<LabelKeyTO> labelKeyTOs = projProcRepository
                .getProjCostCodeItems(StringUtils.join(projGetReq.getProjIds(), ','));
        if (CommonUtil.isListHasData(labelKeyTOs)) {
            for (LabelKeyTO labelKeyTO : labelKeyTOs) {
                labelKeyTOMapResp.getLabelKeyTOList().put(labelKeyTO.getId(), labelKeyTO);
            }
        }
        return labelKeyTOMapResp;
    }

}
