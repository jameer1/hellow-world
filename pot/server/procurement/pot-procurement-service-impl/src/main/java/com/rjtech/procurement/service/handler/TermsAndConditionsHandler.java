package com.rjtech.procurement.service.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.procurement.dto.TermsAndConditionsTO;
import com.rjtech.procurement.model.PreContractEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
import com.rjtech.procurement.model.TermsAndConditionsEntity;
import com.rjtech.procurement.model.TermsAndConditionsFileEntity;
import com.rjtech.procurement.repository.PrecontractRepository;
import com.rjtech.procurement.repository.PurchaseOrderRepository;
import com.rjtech.procurement.repository.TermsAndConditionsRepository;

@Component
public class TermsAndConditionsHandler {

    @Autowired
    private TermsAndConditionsRepository termsAndConditionsRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private PrecontractRepository precontractRepository;
    @Autowired
    private ProjDocFileRepository projDocFileRepository;

    public TermsAndConditionsEntity convertPOJOToEntity(TermsAndConditionsTO termsAndConditionsTO) {
        TermsAndConditionsEntity termsAndConditionsEntity = null;

        if (CommonUtil.isNonBlankLong(termsAndConditionsTO.getId())) {
            termsAndConditionsEntity = termsAndConditionsRepository.findOne(termsAndConditionsTO.getId());
        } else {
            termsAndConditionsEntity = new TermsAndConditionsEntity();
        }

        // set project document files.
        if (!termsAndConditionsTO.getProjDocFileIds().isEmpty()) {
            for (Long fileId : termsAndConditionsTO.getProjDocFileIds()) {
                ProjDocFileEntity projDocFileEntity = projDocFileRepository.findOne(fileId);
                TermsAndConditionsFileEntity termsAndConditionsFileEntity = new TermsAndConditionsFileEntity();
                termsAndConditionsFileEntity.setFile(projDocFileEntity);
                termsAndConditionsEntity.getTermsAndConditionsFiles().add(termsAndConditionsFileEntity);
            }
        }
        // remove deleted terms and conditions files
        if (!termsAndConditionsTO.getDeletedTermsAndConditionsFileIds().isEmpty()) {
            List<TermsAndConditionsFileEntity> filesToBeDeleted = new ArrayList();
            for (Long deletedFileId : termsAndConditionsTO.getDeletedTermsAndConditionsFileIds()) {
                for (TermsAndConditionsFileEntity deletedFile : termsAndConditionsEntity.getTermsAndConditionsFiles()) {
                    if (deletedFile.getId().equals(deletedFileId)) {
                        filesToBeDeleted.add(deletedFile);
                    }
                }
            }
            termsAndConditionsEntity.getTermsAndConditionsFiles().removeAll(filesToBeDeleted);
        }

        if (CommonUtil.isNonBlankLong(termsAndConditionsTO.getPoId())) {
            PurchaseOrderEntity po = purchaseOrderRepository.findOne(termsAndConditionsTO.getPoId());
            termsAndConditionsEntity.setPurchaseOrderEntity(po);
        }
        if (CommonUtil.isNonBlankLong(termsAndConditionsTO.getPreContractId())) {
            PreContractEntity preContractEntity = precontractRepository
                    .findOne(termsAndConditionsTO.getPreContractId());
            termsAndConditionsEntity.setPreContractEntity(preContractEntity);
        }
        termsAndConditionsEntity.setConditionsText(termsAndConditionsTO.getConditionsText());
        return termsAndConditionsEntity;
    }

    public TermsAndConditionsTO convertEntityToPOJO(TermsAndConditionsEntity termsAndConditionsEntity) {
        TermsAndConditionsTO termsAndConditionsTO = new TermsAndConditionsTO();
        termsAndConditionsTO.setId(termsAndConditionsEntity.getId());
        for (TermsAndConditionsFileEntity termsFile : termsAndConditionsEntity.getTermsAndConditionsFiles()) {
            termsAndConditionsTO.getTermsAndConditionsFiles()
                    .add(TermsAndConditionsFileHandler.convertEntityToPOJO(termsFile));
        }
        termsAndConditionsTO.setConditionsText(termsAndConditionsEntity.getConditionsText());
        return termsAndConditionsTO;
    }

    public List<TermsAndConditionsTO> convertEntitiesToPOJO(List<TermsAndConditionsEntity> termsAndConditionsEntities) {
        List<TermsAndConditionsTO> list = new ArrayList<>();
        for (TermsAndConditionsEntity termsAndConditionsEntity : termsAndConditionsEntities) {
            list.add(convertEntityToPOJO(termsAndConditionsEntity));
        }
        return list;
    }

}
