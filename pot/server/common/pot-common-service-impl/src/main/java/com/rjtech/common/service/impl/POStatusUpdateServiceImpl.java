package com.rjtech.common.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rjtech.common.service.POStatusUpdateService;
import com.rjtech.rjs.core.annotations.RJSService;

@Service(value = "poStatusUpdateService")
@RJSService(modulecode = "poStatusUpdateService")
@Transactional
public class POStatusUpdateServiceImpl implements POStatusUpdateService {
//
//    @Autowired
//    private PrecontractEmpCmpRepositoryCopy precontractEmpCmpRepository;
//
//    @Autowired
//    private PrecontractPlantCmpRepositoryCopy precontractPlantCmpRepository;
//
//    @Autowired
//    private PurchaseOrderRepositoryCopy purchaseOrderRepository;
//
//    @Autowired
//    private PrecontractMaterialCmpRepositoryCopy preContractMaterialCmpRepository;
//
//    @Autowired
//    private PrecontractSowCmpRepositoryCopy precontractSowCmpRepository;
//
//    @Autowired
//    private PrecontractServiceCmpRepositoryCopy precontractServiceCmpRepository;
//
    public void updatePurchaseOrderStatus(Long schCmpId, String type, double receviedQty) {
//        switch (type) {
//            case "E":
//                updateEmpPo(schCmpId, type, receviedQty);
//                break;
//            case "P":
//                updatePlantPo( schCmpId, type, receviedQty);
//                break;
//            case "M":
//                updateMaterialPo( schCmpId, type, receviedQty);
//                break;
//            case "S":
//                updateServicePo( schCmpId, type, receviedQty);
//                break;
//            case "SOW":
//                updateSowPo( schCmpId, type, receviedQty);
//                break;
//        }
    }
//
//    private void updateEmpPo(Long schCmpId, String type, double receivedQty) {
//        PreContractsEmpCmpEntityCopy preContractsEmp = precontractEmpCmpRepository.findOne(schCmpId);
//        Integer preReceivedQty = preContractsEmp.getReceivedQty();
//        preContractsEmp.setReceivedQty(
//                (preReceivedQty == null) ? (int) (0 + receivedQty) : (int) (preReceivedQty + receivedQty));
//
//        if (preContractsEmp.getQuantity() - preContractsEmp.getReceivedQty() == 0)
//            preContractsEmp.setComplete(true);
//        else
//            preContractsEmp.setComplete(false);
//
//        preContractsEmp = precontractEmpCmpRepository.save(preContractsEmp);
//        updatePo(purchaseOrderRepository.findByPreContractsCmpEntity(preContractsEmp.getPreContractsCmpEntity()), type);
//    }
//
//    private void updatePlantPo(Long schCmpId, String type, double receviedQty) {
//        PreContractsPlantCmpEntityCopy preContractsPlant = precontractPlantCmpRepository.findOne(schCmpId);
//        Integer preReceivedQty = preContractsPlant.getReceivedQty();
//        preContractsPlant.setReceivedQty(
//                (preReceivedQty == null) ? (int) (0 + receviedQty) : (int) (preReceivedQty + receviedQty));
//
//        if (preContractsPlant.getQuantity() - preContractsPlant.getReceivedQty() == 0)
//            preContractsPlant.setComplete(true);
//        else
//            preContractsPlant.setComplete(false);
//
//        preContractsPlant = precontractPlantCmpRepository.save(preContractsPlant);
//        updatePo(purchaseOrderRepository.findByPreContractsCmpEntity(preContractsPlant.getPreContractsCmpEntity()),
//                type);
//    }
//
//    private void updateMaterialPo(Long schCmpId, String type, double receviedQty) {
//        PreContractsMaterialCmpEntityCopy preContractsMaterialCmp = preContractMaterialCmpRepository.findOne(schCmpId);
//        Long preReceivedQty = preContractsMaterialCmp.getRecievedQty();
//        preContractsMaterialCmp.setRecievedQty(
//                (preReceivedQty == null) ? (long) (0 + receviedQty) : (long) (preReceivedQty + receviedQty));
//
//        if (preContractsMaterialCmp.getQuantity() - preContractsMaterialCmp.getRecievedQty() == 0)
//            preContractsMaterialCmp.setComplete(true);
//        else
//            preContractsMaterialCmp.setComplete(false);
//
//        preContractsMaterialCmp = preContractMaterialCmpRepository.save(preContractsMaterialCmp);
//
//        updatePo(
//                purchaseOrderRepository.findByPreContractsCmpEntity(preContractsMaterialCmp.getPreContractsCmpEntity()),
//                type);
//    }
//
//    private void updateServicePo(Long schCmpId, String type, double receviedQty) {
//        PreContractsServiceCmpEntityCopy preContractsService = precontractServiceCmpRepository.findOne(schCmpId);
//        Integer preReceivedQty = preContractsService.getReceivedQty();
//        preContractsService.setReceivedQty(
//                (preReceivedQty == null) ? (int) (0 + receviedQty) : (int) (preReceivedQty + receviedQty));
//
//        if (preContractsService.getQuantity() - preContractsService.getReceivedQty() == 0)
//            preContractsService.setComplete(true);
//        else
//            preContractsService.setComplete(false);
//
//        preContractsService = precontractServiceCmpRepository.save(preContractsService);
//        updatePo(purchaseOrderRepository.findByPreContractsCmpEntity(preContractsService.getPreContractsCmpEntity()),
//                type);
//    }
//
//    private void updateSowPo(Long schCmpId, String type, double receviedQty) {
//        PrecontractSowCmpEntityCopy preContractsSow = precontractSowCmpRepository.findOne(schCmpId);
//        Integer preReceivedQty = preContractsSow.getReceivedQty();
//        preContractsSow.setReceivedQty(
//                (preReceivedQty == null) ? (int) (0 + receviedQty) : (int) (preReceivedQty + receviedQty));
//
//        if (preContractsSow.getQuantity() - preContractsSow.getReceivedQty() == 0)
//            preContractsSow.setComplete(true);
//        else
//            preContractsSow.setComplete(false);
//
//        preContractsSow = precontractSowCmpRepository.save(preContractsSow);
//        updatePo(purchaseOrderRepository.findByPreContractsCmpEntity(preContractsSow.getPreContractsCmpEntity()), type);
//    }

//  private void updateMaterialPo(Long schCmpId, String type, double receviedQty) {
//  PreContractsMaterialCmpEntityCopy preContractsMaterialCmp = preContractMaterialCmpRepository.findOne(schCmpId);
//  Long preReceivedQty = preContractsMaterialCmp.getRecievedQty();
//  preContractsMaterialCmp.setRecievedQty(
//          (preReceivedQty == null) ? (long) (0 + receviedQty) : (long) (preReceivedQty + receviedQty));
//
//  if (preContractsMaterialCmp.getQuantity() - preContractsMaterialCmp.getRecievedQty() == 0)
//      preContractsMaterialCmp.setComplete(true);
//  else
//      preContractsMaterialCmp.setComplete(false);
//
//  preContractsMaterialCmp = preContractMaterialCmpRepository.save(preContractsMaterialCmp);
//
//  updatePo(
//          purchaseOrderRepository.findByPreContractsCmpEntity(preContractsMaterialCmp.getPreContractsCmpEntity()),
//          type);
//}
    
//    private void updatePo(PurchaseOrderEntityCopy purchaseOrderEntityCopy, String procureType) {
//        String existingType = purchaseOrderEntityCopy.getProcureType();
//        if (CommonUtil.isNotBlankStr(existingType)) {
//            String completeProcureType = purchaseOrderEntityCopy.getCompleteProcureType();
//            String[] completeProcureTypes = completeProcureType.split("#");
//            boolean alreadyCompleted = false;
//            // Checking if procureType is already added in completeProcureTypes
//            for (String complete : completeProcureTypes) {
//                if (complete == procureType) {
//                    alreadyCompleted = true;
//                    break;
//                }
//            }
//            // If procureType is not added, add it
//            if (!alreadyCompleted) {
//                if (completeProcureType != null
//                        && completeProcureType.substring(completeProcureType.length() - 1) == "#") {
//                    completeProcureType = completeProcureType.concat(procureType);
//                    purchaseOrderEntityCopy.setCompleteProcureType(completeProcureType);
//                } else {
//                    purchaseOrderEntityCopy.setCompleteProcureType(procureType);
//                }
//            }
//
//            // Set purchase order as completed, if all types are completed
//            String[] existingTypes = existingType.split("#");
//            completeProcureType = purchaseOrderEntityCopy.getCompleteProcureType();
//            completeProcureTypes = completeProcureType.split("#");
//
//            boolean completedPO = CommonUtil.compareStringArrays(existingTypes, completeProcureTypes);
//            purchaseOrderEntityCopy.setDelivered(completedPO);
//
//        }
//    }
}
