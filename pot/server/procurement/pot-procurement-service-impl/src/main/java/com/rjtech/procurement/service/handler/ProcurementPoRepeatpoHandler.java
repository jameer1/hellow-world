package com.rjtech.procurement.service.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.rjtech.procurement.dto.*;
import com.rjtech.procurement.model.*;
import com.rjtech.procurement.repository.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.AppUtils;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.ModuleCodesPrefixes;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.procurement.req.ProcurementPoRepeatpoGetReq;
import com.rjtech.procurement.req.ProcurementPoRepeatpoSaveReq;
import com.rjtech.procurement.resp.PreContractSowResp;
//import com.rjtech.projectlib.model.copy.ProjSOWItemEntityCopy;
import com.rjtech.projectlib.repository.EPSProjRepository;
import org.springframework.transaction.annotation.Transactional;

public class ProcurementPoRepeatpoHandler {

	public static ProcurementPoRepeatpoGetReq getProcurementRepeatPoTOs(List<PurchaseOrderRepeatEntity> procurementPoRepeatpoEntities,
																		boolean addExternal) {
		ProcurementPoRepeatpoGetReq procurementporepeatpogetreq = new ProcurementPoRepeatpoGetReq();
		ProcurementPoRepeatpoTO procurementPoRepeatpoTO123 = null;
		// ProcurementPoRepeatpoTO procurementPoRepeatpoTO123 = new ProcurementPoRepeatpoTO();

		for (PurchaseOrderRepeatEntity procurementRepeatPoDtlEntity : procurementPoRepeatpoEntities) {

			//if (StatusCodes.ACTIVE.getValue().equals(procurementRepeatPoDtlEntity.getStatus())) {

			if (addExternal) {
				procurementPoRepeatpoTO123 = convertSowDtlEntityToPOJO(procurementRepeatPoDtlEntity);
			}

			//}
			procurementporepeatpogetreq.getProcurementRepeatPODtlTOs().add(procurementPoRepeatpoTO123);

		}
		return procurementporepeatpogetreq;
	}



	private static ProcurementPoRepeatpoTO convertSowDtlEntityToPOJO(PurchaseOrderRepeatEntity procurementRepeatPoDtlEntity) {
		// TODO Auto-generated method stub


		ProcurementPoRepeatpoTO procurementPoRepeatpoTO = new ProcurementPoRepeatpoTO();

		//procurementPoRepeatpoTO.setId(procurementRepeatPoDtlEntity.getId());
		//procurementPoRepeatpoTO.setParentpoid(procurementRepeatPoDtlEntity.getParentPOId().getId());;

		if(procurementRepeatPoDtlEntity.getContractItemType().equalsIgnoreCase("Sow"))
		{
			procurementPoRepeatpoTO.setParentscheduleid(procurementRepeatPoDtlEntity.getContractItemDetailId());
		}

		if (CommonUtil.isNotBlankDate(procurementRepeatPoDtlEntity.getStartDate())) {
			procurementPoRepeatpoTO.setStartDate(CommonUtil.convertDateToString(procurementRepeatPoDtlEntity.getStartDate()));
		}
		if (CommonUtil.isNotBlankDate(procurementRepeatPoDtlEntity.getFinishDate())) {
			procurementPoRepeatpoTO.setFinishDate(CommonUtil.convertDateToString(procurementRepeatPoDtlEntity.getFinishDate()));
		}
		procurementPoRepeatpoTO.setDeliveryplace(procurementRepeatPoDtlEntity.getDeliveryPlace());

		procurementPoRepeatpoTO.setQuantity((int) (long)procurementRepeatPoDtlEntity.getQuantity());
		procurementPoRepeatpoTO.setBid(procurementRepeatPoDtlEntity.getIsBid()==1?true:false);
		//procurementPoRepeatpoTO.setStatus(procurementRepeatPoDtlEntity.getStatus());

		return procurementPoRepeatpoTO;

	}

	public static void displayBeforeSaving(PurchaseOrderRepeatEntity procurementPoRepeatpoEntity)
	{
		System.out.println("displayBeforeSaving procurementporepeatpoentities");

		System.out.println("getId : "+procurementPoRepeatpoEntity.getId());
		System.out.println("getPurchaseorderentity : "+procurementPoRepeatpoEntity.getId());
		//System.out.println("getParentscheduleid : "+procurementPoRepeatpoEntity.getMaterialId()));
		System.out.println("getStartDate : "+procurementPoRepeatpoEntity.getStartDate());
		System.out.println("getFinishDate : "+procurementPoRepeatpoEntity.getFinishDate());
		System.out.println("getDeliveryplace : "+procurementPoRepeatpoEntity.getDeliveryPlace());
		System.out.println("getQuantity : "+procurementPoRepeatpoEntity.getQuantity());
		System.out.println("getBid : "+procurementPoRepeatpoEntity.getIsBid());
		System.out.println("getCreatedBy : "+procurementPoRepeatpoEntity.getCreatedBy());
		System.out.println("getCreatedOn : "+procurementPoRepeatpoEntity.getCreatedOn());
		System.out.println("getUpdatedBy : "+procurementPoRepeatpoEntity.getUpdatedBy());
		System.out.println("getUpdatedOn : "+procurementPoRepeatpoEntity.getUpdatedOn());
		//System.out.println("getStatus : "+procurementPoRepeatpoEntity.getStatus());

	}
	//@Transactional
	public static void savePurchaseOrderRepeatEntity(PurchaseOrderRepeatEntity procurementPoRepeatpoEntity, PurchaseOrderRepeatRepository procurementporepeatporepository)
	{
		System.out.println("Started: Handler : savePurchaseOrderRepeatEntity");
		displayBeforeSaving(procurementPoRepeatpoEntity);

		procurementporepeatporepository.save(procurementPoRepeatpoEntity);
		System.out.println("Ended : savePurchaseOrderRepeatEntity");
	}


	//VIZ 1
	public static PurchaseOrderRepeatEntity getRepeatPOPreContractMaterial(PreContractMaterialDtlTO preContractMaterialDtlTO,
																		   boolean version, Long purchaseOrderEntityId, HashMap<String,String> deliveryPlaceMap) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = new PurchaseOrderRepeatEntity();
		System.out.println("getRepeatPOPreContractMaterial version : "+version+" : : "+preContractMaterialDtlTO.getId());


		purchaseOrderRepeatEntity.setParentPOId(purchaseOrderEntityId);
		purchaseOrderRepeatEntity.setContractItemType("Material");
		purchaseOrderRepeatEntity.setContractItemDetailId(preContractMaterialDtlTO.getId());
		purchaseOrderRepeatEntity.setStartDate(CommonUtil.convertStringToDate(preContractMaterialDtlTO.getStartDate()));
		purchaseOrderRepeatEntity.setFinishDate(CommonUtil.convertStringToDate(preContractMaterialDtlTO.getFinishDate()));
		System.out.println("preContractEmpDtlTO.getPreContractMaterialCmpTOs().size: "+preContractMaterialDtlTO.getPreContractMaterialCmpTOs().size());
		purchaseOrderRepeatEntity.setContractId(preContractMaterialDtlTO.getPreContractId());
		// for DeliveryMap
		if(deliveryPlaceMap!=null)
		{
			String deliveryPlace = deliveryPlaceMap.get("ProjStoreId")+":"+deliveryPlaceMap.get("StoreId");
			purchaseOrderRepeatEntity.setDeliveryPlace(deliveryPlace);
			System.out.println("*** After Strong DeliveryPlace : "+purchaseOrderRepeatEntity.getDeliveryPlace());
		}else
			System.out.println("deliveryPlaceMap is NULL");


		// Why Multiple or FOR Loop here?
		for (PreContractMaterialCmpTO preContractMaterialCmpTO : preContractMaterialDtlTO.getPreContractMaterialCmpTOs()) {
			System.out.println("PreContractMaterialCmpTO : Quantity : "+preContractMaterialCmpTO.getQuantity()+" : isApproveFlag : "+preContractMaterialCmpTO.isApproveFlag());
			purchaseOrderRepeatEntity.setQuantity(Long.valueOf(preContractMaterialCmpTO.getQuantity()));
			purchaseOrderRepeatEntity.setIsBid(preContractMaterialCmpTO.isApproveFlag()==true?1:2);
			purchaseOrderRepeatEntity.setContractItemRate(preContractMaterialCmpTO.getRate());
			purchaseOrderRepeatEntity.setContractItemId(preContractMaterialCmpTO.getId());
			purchaseOrderRepeatEntity.setContractCmpId(preContractMaterialCmpTO.getPreContractCmpId());
			//TODO purchaseOrderRepeatEntity.setDeliveryPlace(preContractMaterialDtlTO.getd);
		}
		System.out.println("** New Items ContractId:"+purchaseOrderRepeatEntity.getContractId() +": ContractItemId :" + purchaseOrderRepeatEntity.getContractItemId() +": ContractCmpId : "+ purchaseOrderRepeatEntity.getContractCmpId());

		return purchaseOrderRepeatEntity;
	}
	//VIZ 2
	public static PurchaseOrderRepeatEntity getRepeatPOPreContractEmpDtl(PreContractEmpDtlTO preContractEmpDtlTO, boolean version,Long purchaseOrderEntityId) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = new PurchaseOrderRepeatEntity();
		System.out.println("getRepeatPOPreContractMaterial version : "+version+" : : "+preContractEmpDtlTO.getId());

		//preContractEmpDtlTO.getPreContractsEmpCmpTOs().get(0).isApproveFlag()
		purchaseOrderRepeatEntity.setParentPOId(purchaseOrderEntityId);
		purchaseOrderRepeatEntity.setContractItemType("Employee");
		purchaseOrderRepeatEntity.setContractItemDetailId(preContractEmpDtlTO.getId());
		purchaseOrderRepeatEntity.setStartDate(CommonUtil.convertStringToDate(preContractEmpDtlTO.getStartDate()));
		purchaseOrderRepeatEntity.setFinishDate(CommonUtil.convertStringToDate(preContractEmpDtlTO.getFinishDate()));

		purchaseOrderRepeatEntity.setContractId(preContractEmpDtlTO.getPreContractId());
		/*if(preContractEmpDtlTO.getPreContractsEmpCmpTO()!=null)
		{
			if(preContractEmpDtlTO.getPreContractsEmpCmpTO().getQuantity()!=null)
			{
				purchaseOrderRepeatEntity.setQuantity(Long.valueOf(preContractEmpDtlTO.getPreContractsEmpCmpTO().getQuantity()));
			}
			purchaseOrderRepeatEntity.setIsBid(preContractEmpDtlTO.getPreContractsEmpCmpTO().isApproveFlag()==true?1:2);
		}*/

		System.out.println("preContractEmpDtlTO.getPreContractsEmpCmpTOs().size: "+preContractEmpDtlTO.getPreContractsEmpCmpTOs().size());
		// Why Multiple or FOR Loop here?
		for (PreContractsEmpCmpTO preContractsEmpCmpTO : preContractEmpDtlTO.getPreContractsEmpCmpTOs()) {
			System.out.println("PreContractsEmpCmpTO : Quantity : "+preContractsEmpCmpTO.getQuantity()+" : isApproveFlag : "+preContractsEmpCmpTO.isApproveFlag());
			purchaseOrderRepeatEntity.setQuantity(Long.valueOf(preContractsEmpCmpTO.getQuantity()));
			purchaseOrderRepeatEntity.setIsBid(preContractsEmpCmpTO.isApproveFlag()==true?1:2);
			purchaseOrderRepeatEntity.setContractItemRate(preContractsEmpCmpTO.getRate());
			purchaseOrderRepeatEntity.setContractItemId(preContractsEmpCmpTO.getId());
			purchaseOrderRepeatEntity.setContractCmpId(preContractsEmpCmpTO.getPreContractCmpId());
			purchaseOrderRepeatEntity.setDeliveryPlace(preContractEmpDtlTO.getDeliveryPlace());
		}

		System.out.println("** New Items ContractId:"+purchaseOrderRepeatEntity.getContractId() +": ContractItemId :" + purchaseOrderRepeatEntity.getContractItemId() +": ContractCmpId : "+ purchaseOrderRepeatEntity.getContractCmpId());


		return purchaseOrderRepeatEntity;
	}

	// VIZ 3
	public static PurchaseOrderRepeatEntity getRepeatPOPreContractPlantDtl(PreContractPlantDtlTO preContractPlantDtlTO, boolean version,Long purchaseOrderEntityId) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = new PurchaseOrderRepeatEntity();
		System.out.println("getRepeatPOPreContractMaterial version : "+version+" : : "+preContractPlantDtlTO.getId());

		//preContractEmpDtlTO.getPreContractsEmpCmpTOs().get(0).isApproveFlag()
		purchaseOrderRepeatEntity.setParentPOId(purchaseOrderEntityId);
		purchaseOrderRepeatEntity.setContractItemType("Plant");
		purchaseOrderRepeatEntity.setContractItemDetailId(preContractPlantDtlTO.getId());
		purchaseOrderRepeatEntity.setStartDate(CommonUtil.convertStringToDate(preContractPlantDtlTO.getStartDate()));
		purchaseOrderRepeatEntity.setFinishDate(CommonUtil.convertStringToDate(preContractPlantDtlTO.getFinishDate()));

		purchaseOrderRepeatEntity.setContractId(preContractPlantDtlTO.getPreContractId());
		/*if(preContractPlantDtlTO.getPreContractPlantCmpTO()!=null)
		{
			if(preContractPlantDtlTO.getPreContractPlantCmpTO().getQuantity()!=null)
			{
				purchaseOrderRepeatEntity.setQuantity(Long.valueOf(preContractPlantDtlTO.getPreContractPlantCmpTO().getQuantity()));
			}
			purchaseOrderRepeatEntity.setIsBid(preContractPlantDtlTO.getPreContractPlantCmpTO().isApproveFlag()==true?1:2);
		}*/

		System.out.println(" preContractPlantDtlTO.getPreContractPlantCmpTOs  size: "+preContractPlantDtlTO.getPreContractPlantCmpTOs().size());
		// Why Multiple or FOR Loop here?
		for (PreContractPlantCmpTO preContractPlantCmpTO : preContractPlantDtlTO.getPreContractPlantCmpTOs()) {
			System.out.println("preContractPlantCmpTO : Quantity : "+preContractPlantCmpTO.getQuantity()+" : isApproveFlag : "+preContractPlantCmpTO.isApproveFlag());
			purchaseOrderRepeatEntity.setQuantity(Long.valueOf(preContractPlantCmpTO.getQuantity()));
			purchaseOrderRepeatEntity.setIsBid(preContractPlantCmpTO.isApproveFlag()==true?1:2);
			purchaseOrderRepeatEntity.setContractItemRate(preContractPlantCmpTO.getRate());
			purchaseOrderRepeatEntity.setContractItemId(preContractPlantCmpTO.getId());
			purchaseOrderRepeatEntity.setContractCmpId(preContractPlantCmpTO.getPreContractCmpId());
			purchaseOrderRepeatEntity.setDeliveryPlace(preContractPlantDtlTO.getDeliveryPlace());
		}

		System.out.println("** New Items ContractId:"+purchaseOrderRepeatEntity.getContractId() +": ContractItemId :" + purchaseOrderRepeatEntity.getContractItemId() +": ContractCmpId : "+ purchaseOrderRepeatEntity.getContractCmpId());


		return purchaseOrderRepeatEntity;
	}

	// VIZ 4
	public static PurchaseOrderRepeatEntity getRepeatPOPreContractServiceDtl(PreContractServiceDtlTO preContractServiceDtlTO, boolean version,Long purchaseOrderEntityId) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = new PurchaseOrderRepeatEntity();
		System.out.println("getRepeatPOPreContractMaterial version : "+version+" : : "+preContractServiceDtlTO.getId());

		//preContractEmpDtlTO.getPreContractsEmpCmpTOs().get(0).isApproveFlag()
		purchaseOrderRepeatEntity.setParentPOId(purchaseOrderEntityId);
		purchaseOrderRepeatEntity.setContractItemType("Service");
		purchaseOrderRepeatEntity.setContractItemDetailId(preContractServiceDtlTO.getId());
		purchaseOrderRepeatEntity.setStartDate(CommonUtil.convertStringToDate(preContractServiceDtlTO.getStartDate()));
		purchaseOrderRepeatEntity.setFinishDate(CommonUtil.convertStringToDate(preContractServiceDtlTO.getFinishDate()));

		purchaseOrderRepeatEntity.setContractId(preContractServiceDtlTO.getPreContractId());
		/*if(preContractServiceDtlTO.getPreContractServiceCmpTO()!=null)
		{
			if(preContractServiceDtlTO.getPreContractServiceCmpTO().getQuantity()!=null)
			{
				purchaseOrderRepeatEntity.setQuantity(Long.valueOf(preContractServiceDtlTO.getPreContractServiceCmpTO().getQuantity()));
			}
			purchaseOrderRepeatEntity.setIsBid(preContractServiceDtlTO.getPreContractServiceCmpTO().isApproveFlag()==true?1:2);
		}*/

		System.out.println(" preContractPlantDtlTO.getPreContractServiceCmpTOs  size: "+preContractServiceDtlTO.getPreContractServiceCmpTOs().size());
		// Why Multiple or FOR Loop here?
		for (PreContractServiceCmpTO preContractServiceCmpTO : preContractServiceDtlTO.getPreContractServiceCmpTOs()) {
			System.out.println("preContractPlantCmpTO : Quantity : "+preContractServiceCmpTO.getQuantity()+" : isApproveFlag : "+preContractServiceCmpTO.isApproveFlag());
			purchaseOrderRepeatEntity.setQuantity(Long.valueOf(preContractServiceCmpTO.getQuantity()));
			purchaseOrderRepeatEntity.setIsBid(preContractServiceCmpTO.isApproveFlag()==true?1:2);
			purchaseOrderRepeatEntity.setContractItemRate(preContractServiceCmpTO.getRate());
			purchaseOrderRepeatEntity.setContractItemId(preContractServiceCmpTO.getId());
			purchaseOrderRepeatEntity.setContractCmpId(preContractServiceCmpTO.getPreContractCmpId());
			purchaseOrderRepeatEntity.setDeliveryPlace(preContractServiceDtlTO.getDeliveryPlace());
		}

		System.out.println("** New Items ContractId:"+purchaseOrderRepeatEntity.getContractId() +": ContractItemId :" + purchaseOrderRepeatEntity.getContractItemId() +": ContractCmpId : "+ purchaseOrderRepeatEntity.getContractCmpId());


		return purchaseOrderRepeatEntity;
	}

	// VIZ 5
	public static PurchaseOrderRepeatEntity getRepeatPOPrecontractSowDtl(PrecontractSowDtlTO precontractSowDtlTO, boolean version,Long purchaseOrderEntityId) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity purchaseOrderRepeatEntity = new PurchaseOrderRepeatEntity();
		System.out.println("getRepeatPOPreContractMaterial version : "+version+" : : "+precontractSowDtlTO.getId());

		//preContractEmpDtlTO.getPreContractsEmpCmpTOs().get(0).isApproveFlag()
		purchaseOrderRepeatEntity.setParentPOId(purchaseOrderEntityId);
		purchaseOrderRepeatEntity.setContractItemType("Sow");
		purchaseOrderRepeatEntity.setContractItemDetailId(precontractSowDtlTO.getId());
		purchaseOrderRepeatEntity.setStartDate(CommonUtil.convertStringToDate(precontractSowDtlTO.getStartDate()));
		purchaseOrderRepeatEntity.setFinishDate(CommonUtil.convertStringToDate(precontractSowDtlTO.getFinishDate()));

		purchaseOrderRepeatEntity.setContractId(precontractSowDtlTO.getPreContractId());
		/*if(precontractSowDtlTO.getPrecontractSowCmpTO()!=null)
		{
			if(precontractSowDtlTO.getPrecontractSowCmpTO().getQuantity()!=null)
			{
				purchaseOrderRepeatEntity.setQuantity(Long.valueOf(precontractSowDtlTO.getPrecontractSowCmpTO().getQuantity()));
			}
			purchaseOrderRepeatEntity.setIsBid(precontractSowDtlTO.getPrecontractSowCmpTO().isApproveFlag()==true?1:2);
		}*/

		System.out.println(" preContractPlantDtlTO.getPreContractServiceCmpTOs  size: "+precontractSowDtlTO.getPrecontractSowCmpTOs().size());
		// Why Multiple or FOR Loop here?
		for (PrecontractSowCmpTO precontractSowCmpTO : precontractSowDtlTO.getPrecontractSowCmpTOs()) {
			System.out.println("precontractSowCmpTO : Quantity : "+precontractSowCmpTO.getQuantity()+" : isApproveFlag : "+precontractSowCmpTO.isApproveFlag());
			purchaseOrderRepeatEntity.setQuantity(Long.valueOf(precontractSowCmpTO.getQuantity()));
			purchaseOrderRepeatEntity.setIsBid(precontractSowCmpTO.isApproveFlag()==true?1:2);
			purchaseOrderRepeatEntity.setContractItemRate(precontractSowCmpTO.getRate());
			purchaseOrderRepeatEntity.setContractItemId(precontractSowCmpTO.getId());
			purchaseOrderRepeatEntity.setContractCmpId(precontractSowCmpTO.getPreContractCmpId());
			purchaseOrderRepeatEntity.setDeliveryPlace(precontractSowDtlTO.getDeliveryPlace());
		}

		System.out.println("** New Items ContractId:"+purchaseOrderRepeatEntity.getContractId() +": ContractItemId :" + purchaseOrderRepeatEntity.getContractItemId() +": ContractCmpId : "+ purchaseOrderRepeatEntity.getContractCmpId());

		return purchaseOrderRepeatEntity;
	}

	public static PurchaseOrderRepeatEntity convertRepeatpoPOJOToEntity(PreContractEmpDtlTO preContractEmpDtlTO,
																		boolean version, PurchaseOrderEntity parentPurchaseOrderEntity) {
		PurchaseOrderRepeatEntity procurementPoRepeatpoentity = new PurchaseOrderRepeatEntity();
	       /* if (!version && CommonUtil.isNonBlankLong(preContractEmpDtlTO.getId())) {
	        	procurementPoRepeatpoentity.setId(preContractEmpDtlTO.getId());
	        }*/
		//procurementPoRepeatpoentity.getPurchaseorderentity().setId(parentPurchaseOrderEntity.getId());
		//procurementPoRepeatpoentity.setParentscheduleid(preContractEmpDtlTO.getId());
		procurementPoRepeatpoentity.setStartDate(CommonUtil.convertStringToDate(preContractEmpDtlTO.getStartDate()));
		procurementPoRepeatpoentity.setFinishDate(CommonUtil.convertStringToDate(preContractEmpDtlTO.getFinishDate()));
		//procurementPoRepeatpoentity.setDeliveryplace(preContractEmpDtlTO.getDeliveryPlace());

		//procurementPoRepeatpoentity.setQuantity(preContractEmpDtlTO.getQuantity());
		//procurementPoRepeatpoentity.setBid(preContractEmpDtlTO.getLatest());
		//procurementPoRepeatpoentity.setStatus(preContractEmpDtlTO.getStatus());

		return procurementPoRepeatpoentity;
	}

	public static PurchaseOrderRepeatEntity convertRepeatpoPOJOToEntity(PreContractPlantDtlTO preContractPlantDtlTO,
																		boolean version,PurchaseOrderEntity parentPurchaseOrderEntity) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity procurementPoRepeatpoentity = new PurchaseOrderRepeatEntity();
	        /*if (!version && CommonUtil.isNonBlankLong(preContractPlantDtlTO.getId())) {
	        	procurementPoRepeatpoentity.setId(preContractPlantDtlTO.getId());
	        }*/
		//procurementPoRepeatpoentity.getPurchaseorderentity().setId(parentPurchaseOrderEntity.getId());
		//procurementPoRepeatpoentity.setParentscheduleid(preContractPlantDtlTO.getId());
		procurementPoRepeatpoentity.setStartDate(CommonUtil.convertStringToDate(preContractPlantDtlTO.getStartDate()));
		procurementPoRepeatpoentity.setFinishDate(CommonUtil.convertStringToDate(preContractPlantDtlTO.getFinishDate()));
		//procurementPoRepeatpoentity.setDeliveryplace(preContractPlantDtlTO.getDeliveryPlace());
		//procurementPoRepeatpoentity.setQuantity(preContractPlantDtlTO.getQuantity());
		//procurementPoRepeatpoentity.setBid(preContractPlantDtlTO.getLatest());
		//procurementPoRepeatpoentity.setStatus(preContractPlantDtlTO.getStatus());

		return procurementPoRepeatpoentity;
	}


	public static PurchaseOrderRepeatEntity convertRepeatpoPOJOToEntity(
			PreContractMaterialDtlTO preContractMaterialDtlTO, boolean version,PurchaseOrderEntity parentPurchaseOrderEntity) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity procurementPoRepeatpoentity = new PurchaseOrderRepeatEntity();
        /*if (!version && CommonUtil.isNonBlankLong(preContractMaterialDtlTO.getId())) {
        	procurementPoRepeatpoentity.setId(preContractMaterialDtlTO.getId());
        }*/
		//procurementPoRepeatpoentity.getPurchaseorderentity().setId(parentPurchaseOrderEntity.getId());
		//procurementPoRepeatpoentity.setParentscheduleid(preContractMaterialDtlTO.getId());
		procurementPoRepeatpoentity.setStartDate(CommonUtil.convertStringToDate(preContractMaterialDtlTO.getStartDate()));
		procurementPoRepeatpoentity.setFinishDate(CommonUtil.convertStringToDate(preContractMaterialDtlTO.getFinishDate()));
		// procurementPoRepeatpoentity.setDeliveryplace(preContractMaterialDtlTO.get);
		//procurementPoRepeatpoentity.setQuantity(preContractMaterialDtlTO.getQuantity());
		//procurementPoRepeatpoentity.setBid(preContractMaterialDtlTO.getLatest());
		//procurementPoRepeatpoentity.setStatus(preContractMaterialDtlTO.getStatus());

		return procurementPoRepeatpoentity;
	}

	public static PurchaseOrderRepeatEntity convertRepeatpoPOJOToEntity(
			PreContractServiceDtlTO preContractServiceDtlTO, boolean version,PurchaseOrderEntity parentPurchaseOrderEntity) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity procurementPoRepeatpoentity = new PurchaseOrderRepeatEntity();
	        /*if (!version && CommonUtil.isNonBlankLong(preContractServiceDtlTO.getId())) {
	        	procurementPoRepeatpoentity.setId(preContractServiceDtlTO.getId());
	        }*/

		//procurementPoRepeatpoentity.getPurchaseorderentity().setId(parentPurchaseOrderEntity.getId());
		//procurementPoRepeatpoentity.setParentscheduleid(preContractServiceDtlTO.getId());
		procurementPoRepeatpoentity.setStartDate(CommonUtil.convertStringToDate(preContractServiceDtlTO.getStartDate()));
		procurementPoRepeatpoentity.setFinishDate(CommonUtil.convertStringToDate(preContractServiceDtlTO.getFinishDate()));
		//procurementPoRepeatpoentity.setDeliveryplace(preContractServiceDtlTO.getDeliveryPlace());
		//procurementPoRepeatpoentity.setQuantity(preContractServiceDtlTO.getQuantity());
		//procurementPoRepeatpoentity.setBid(preContractServiceDtlTO.getLatest());
		//procurementPoRepeatpoentity.setStatus(preContractServiceDtlTO.getStatus());

		return procurementPoRepeatpoentity;
	}

	public static PurchaseOrderRepeatEntity convertRepeatpoPOJOToEntity(PrecontractSowDtlTO precontractSowDtlTO,
																		boolean version,PurchaseOrderEntity parentPurchaseOrderEntity) {
		// TODO Auto-generated method stub
		PurchaseOrderRepeatEntity procurementPoRepeatpoentity = new PurchaseOrderRepeatEntity();
	        /*if (!version && CommonUtil.isNonBlankLong(precontractSowDtlTO.getId())) {
	        	procurementPoRepeatpoentity.setId(precontractSowDtlTO.getId());
	        }*/
		//procurementPoRepeatpoentity.getPurchaseorderentity().setId(parentPurchaseOrderEntity.getId());
		//procurementPoRepeatpoentity.setParentscheduleid(precontractSowDtlTO.getId());
		procurementPoRepeatpoentity.setStartDate(CommonUtil.convertStringToDate(precontractSowDtlTO.getStartDate()));
		procurementPoRepeatpoentity.setFinishDate(CommonUtil.convertStringToDate(precontractSowDtlTO.getFinishDate()));
		//procurementPoRepeatpoentity.setDeliveryplace(precontractSowDtlTO.getDeliveryPlace());
		//procurementPoRepeatpoentity.setQuantity((int) (long) precontractSowDtlTO.getQuantity());
		//procurementPoRepeatpoentity.setBid(precontractSowDtlTO.getLatest());
		//procurementPoRepeatpoentity.setStatus(precontractSowDtlTO.getStatus());

		return procurementPoRepeatpoentity;
	}
}