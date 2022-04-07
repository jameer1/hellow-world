package com.rjtech.register.service.handler.emp;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.procurement.model.PreContractsEmpCmpEntity;
import com.rjtech.procurement.model.PreContractsEmpDtlEntity;
import com.rjtech.procurement.model.PreContractsServiceCmpEntity;
import com.rjtech.procurement.model.PreContractsServiceDtlEntity;
import com.rjtech.procurement.model.PurchaseOrderEntity;
/*import com.rjtech.procurement.model.PreContractsEmpCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsEmpDtlEntityCopy;
import com.rjtech.procurement.model.PreContractsServiceCmpEntityCopy;
import com.rjtech.procurement.model.PreContractsServiceDtlEntityCopy;
import com.rjtech.procurement.model.PurchaseOrderEntityCopy;*/
import com.rjtech.procurement.repository.PrecontractEmpCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractEmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceCmpRepositoryCopy;
import com.rjtech.procurement.repository.PrecontractServiceRepositoryCopy;
import com.rjtech.procurement.repository.PurchaseOrderRepositoryCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.register.emp.dto.EmpEnrollmentDtlTO;
import com.rjtech.register.emp.model.EmpProjRegisterPODtlEntity;

public class ProjEmpRegisterPODtlHandler {

    public static void convertEmpPOPOJOToEntity(EmpEnrollmentDtlTO empEnrollmentDtlTO,
            EmpProjRegisterPODtlEntity entity, EPSProjRepository epsProjRepository,
            PurchaseOrderRepositoryCopy purchaseOrderRepository, PrecontractEmpRepositoryCopy precontractEmpRepository,
            PrecontractEmpCmpRepositoryCopy precontractEmpCmpRepository,
            PrecontractServiceCmpRepositoryCopy precontractServiceCmpRepository,
            PrecontractServiceRepositoryCopy precontractServiceRepository) {
        if (CommonUtil.objectNotNull(empEnrollmentDtlTO.getProjId())) {
            ProjMstrEntity projMstrEntity = epsProjRepository.findOne(empEnrollmentDtlTO.getProjId());
            entity.setProjMstrEntity(projMstrEntity);
        }
        if (CommonUtil.objectNotNull(empEnrollmentDtlTO.getProjectPOTO().getPurchaseLabelKeyTO())) {
            if (CommonUtil.isNonBlankLong(empEnrollmentDtlTO.getProjectPOTO().getPurchaseLabelKeyTO().getId())) {
                PurchaseOrderEntity purchaseOrderEntity = purchaseOrderRepository
                        .findOne(empEnrollmentDtlTO.getProjectPOTO().getPurchaseLabelKeyTO().getId());
                entity.setPurchaseOrderEntity(purchaseOrderEntity);
            }
        }
        if (CommonUtil.objectNotNull(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO())) {
            String schItemType = empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getDisplayNamesMap()
                    .get(CommonConstants.PROCUREMENT_MASTER_TYPE);
            Long schItemId = empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getId();
            Long cmpId = Long.valueOf(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO()
                    .getDisplayNamesMap().get(CommonConstants.SCH_CMP_ID));
            if (CommonUtil.isNonBlankLong(schItemId)) {
                if (schItemType.equals("E")) {
                    PreContractsEmpDtlEntity preContractsEmpDtlEntity = precontractEmpRepository.findOne(schItemId);
                    entity.setPreContractsEmpDtlEntity(preContractsEmpDtlEntity);

                    PreContractsEmpCmpEntity preContractsEmpCmpEntity = precontractEmpCmpRepository.findOne(cmpId);
                    entity.setPreContractsEmpCmpEntity(preContractsEmpCmpEntity);
                } else if (schItemType.equals("S")) {
                    PreContractsServiceDtlEntity preContractsServiceDtlEntity = precontractServiceRepository
                            .findOne(schItemId);
                    entity.setPreContractsServiceDtlEntity(preContractsServiceDtlEntity);

                    PreContractsServiceCmpEntity preContractsServiceCmpEntity = precontractServiceCmpRepository
                            .findOne(cmpId);
                    entity.setPreContractsServiceCmpEntity(preContractsServiceCmpEntity);
                }

            }
            // TODO remove this, if you are setting same data in PreContractsEmpDtlEntity
            entity.setPurchaseItemDesc(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getName());
            entity.setActualItems(Long.valueOf(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO()
                    .getDisplayNamesMap().get(CommonConstants.QTY)));
            entity.setPurchaseSchItemCode(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getCode());
            entity.setPurchaseSchItemId(empEnrollmentDtlTO.getProjectPOTO().getPurchaseSchLabelKeyTO().getId());
        
        }

        entity.setStatus(empEnrollmentDtlTO.getStatus());

    }

}
