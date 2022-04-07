package com.rjtech.register.service.handler.material;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.model.StockMstrEntity;
import com.rjtech.centrallib.repository.StockRepository;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.projectlib.model.ProjStoreStockMstrEntity;
//import com.rjtech.projectlib.model.ProjStoreStockMstrEntityCopy;
import com.rjtech.register.material.dto.MaterialPODeliveryDocketTO;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.material.model.MaterialProjDtlEntity;
import com.rjtech.register.repository.material.CopyProjStoreStockRepository;
import com.rjtech.document.model.ProjDocFileEntity;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.document.repository.ProjDocFileRepository;
import com.rjtech.common.utils.UploadUtil;
import com.rjtech.eps.model.ProjMstrEntity;

public class MaterialPODeliveryDocketHandler {

    public static MaterialPODeliveryDocketTO convertEntityToPOJO(MaterialPODeliveryDocketEntity entity) {

        MaterialPODeliveryDocketTO materialPODeliveryDocketTO = new MaterialPODeliveryDocketTO();
        materialPODeliveryDocketTO.setId(entity.getId());
        materialPODeliveryDocketTO.setRegMaterialId(entity.getMaterialProjDtlEntity().getId());
        materialPODeliveryDocketTO.setDocketNumber(entity.getDocketNumber());

        if (CommonUtil.isNotBlankDate(entity.getSupplyDeliveryDate())) {
            materialPODeliveryDocketTO
                    .setSupplyDeliveryDate(CommonUtil.convertDateToString(entity.getSupplyDeliveryDate()));
        }

        if (CommonUtil.isNotBlankDate(entity.getDocketDate())) {
            materialPODeliveryDocketTO.setDocketDate(CommonUtil.convertDateToString(entity.getDocketDate()));
        }

        LabelKeyTO stockLabelKeyTO = new LabelKeyTO();
        StockMstrEntity stockMstrEntity = entity.getStockId();
        if (null != stockMstrEntity) {
            stockLabelKeyTO.setId(stockMstrEntity.getId());
        }
        stockLabelKeyTO.setCode(entity.getStockCode());
        materialPODeliveryDocketTO.setStockLabelKeyTO(stockLabelKeyTO);

        LabelKeyTO projStockLabelKeyTO = new LabelKeyTO();
        ProjStoreStockMstrEntity projStoreStockMstrEntity = entity.getProjStockId();
        if (projStoreStockMstrEntity != null) {
            projStockLabelKeyTO.setId(projStoreStockMstrEntity.getId());
        }
        projStockLabelKeyTO.setCode(entity.getProjStockCode());
        materialPODeliveryDocketTO.setProjStockLabelKeyTO(projStockLabelKeyTO);
        materialPODeliveryDocketTO.setReceivedQty(entity.getReceivedQty());
        materialPODeliveryDocketTO.setReceivedBy(entity.getReceivedBy());
        materialPODeliveryDocketTO.setDefectComments(entity.getDefectComments());
        materialPODeliveryDocketTO.setComments(entity.getComments());
        materialPODeliveryDocketTO.setSourceType(entity.getSourceType());
        materialPODeliveryDocketTO.setLocType(entity.getLocType());
        materialPODeliveryDocketTO.setName(entity.getFileName());
        materialPODeliveryDocketTO.setFileType(entity.getFileType());
        materialPODeliveryDocketTO.setFileSize(entity.getFileSize());
        materialPODeliveryDocketTO.setFileKey(entity.getFileKey());
        materialPODeliveryDocketTO.setStatus(entity.getStatus());
        return materialPODeliveryDocketTO;
    }

    /*public static List<MaterialPODeliveryDocketEntity> convertPOJOsToEntities(
            List<MaterialPODeliveryDocketTO> materialPODeliveryDocketTOs, StockRepository stockRepository,
            CopyProjStoreStockRepository projStoreStockRepository) {
        List<MaterialPODeliveryDocketEntity> materialPODeliveryDocketEntities = new ArrayList<>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        for (MaterialPODeliveryDocketTO materialPODeliveryDocketTO : materialPODeliveryDocketTOs) {
            materialPODeliveryDocketEntities
                    .add(convertPOJOToEntity(materialPODeliveryDocketTO, stockRepository, projStoreStockRepository));
        }
        return materialPODeliveryDocketEntities;
    }*/

    public static MaterialPODeliveryDocketEntity convertPOJOToEntity(
            MaterialPODeliveryDocketTO materialPODeliveryDocketTO, StockRepository stockRepository,
            CopyProjStoreStockRepository projStoreStockRepository, ProjDocFolderEntity projDocFolderEntity, ProjDocFileRepository projDocFileRepository, Long userId, Long projectId, String upload_path ) {
        MaterialPODeliveryDocketEntity entity = new MaterialPODeliveryDocketEntity();

        if (CommonUtil.isNonBlankLong(materialPODeliveryDocketTO.getId())) {
            entity.setId(materialPODeliveryDocketTO.getId());
        }

        entity.setDocketNumber(materialPODeliveryDocketTO.getDocketNumber());

        if (CommonUtil.isNotBlankStr(materialPODeliveryDocketTO.getSupplyDeliveryDate())) {
            entity.setSupplyDeliveryDate(
                    CommonUtil.convertStringToDate(materialPODeliveryDocketTO.getSupplyDeliveryDate()));
        }

        if (CommonUtil.isNotBlankStr(materialPODeliveryDocketTO.getDocketDate())) {
            entity.setDocketDate(CommonUtil.convertStringToDate(materialPODeliveryDocketTO.getDocketDate()));
        }

        if (CommonUtil.objectNotNull(materialPODeliveryDocketTO.getStockLabelKeyTO())
                && materialPODeliveryDocketTO.getStockLabelKeyTO().getId() != null) {
            StockMstrEntity stockMstrEntity = stockRepository
                    .findOne(materialPODeliveryDocketTO.getStockLabelKeyTO().getId());
            entity.setStockId(stockMstrEntity);
            entity.setStockCode(stockMstrEntity.getCode());
        }

        if (CommonUtil.objectNotNull(materialPODeliveryDocketTO.getProjStockLabelKeyTO())
                && materialPODeliveryDocketTO.getProjStockLabelKeyTO().getId() != null) {
            ProjStoreStockMstrEntity projStoreStockMstrEntity = projStoreStockRepository
                    .findOne(materialPODeliveryDocketTO.getProjStockLabelKeyTO().getId());
            if (null != projStoreStockMstrEntity) {
                entity.setProjStockId(projStoreStockMstrEntity);
            }
            entity.setProjStockCode(materialPODeliveryDocketTO.getProjStockLabelKeyTO().getCode());
        }
        entity.setReceivedQty(materialPODeliveryDocketTO.getReceivedQty());
        entity.setReceivedBy(materialPODeliveryDocketTO.getReceivedBy());
        entity.setDefectComments(materialPODeliveryDocketTO.getDefectComments());
        entity.setComments(materialPODeliveryDocketTO.getComments());
        entity.setSourceType(materialPODeliveryDocketTO.getSourceType());
        entity.setLocType(materialPODeliveryDocketTO.getLocType());
        entity.setStatus(materialPODeliveryDocketTO.getStatus());

        if (CommonUtil.isNonBlankLong(materialPODeliveryDocketTO.getRegMaterialId())) {
            MaterialProjDtlEntity materialProjDtlEntity = new MaterialProjDtlEntity();
            materialProjDtlEntity.setId(materialPODeliveryDocketTO.getRegMaterialId());
            entity.setMaterialProjDtlEntity(materialProjDtlEntity);
        }

        if (materialPODeliveryDocketTO.getName() != null) {
        	UserMstrEntity userMstrEntity = new UserMstrEntity();
        	userMstrEntity.setUserId(userId);
        	UploadUtil uploadUtil = new UploadUtil();
        	
            entity.setFileName(materialPODeliveryDocketTO.getName());
            entity.setFileSize(materialPODeliveryDocketTO.getFileSize());
            entity.setFileType(materialPODeliveryDocketTO.getFileType());
            
            ProjDocFileEntity projDocFileEntity = new ProjDocFileEntity();
            ProjDocFileEntity resProjDocFileEntity = new ProjDocFileEntity();
			
			projDocFileEntity.setName( materialPODeliveryDocketTO.getName() );
			projDocFileEntity.setFolderId( projDocFolderEntity );
			projDocFileEntity.setFileSize( materialPODeliveryDocketTO.getFileSize() );
			projDocFileEntity.setFileType( materialPODeliveryDocketTO.getFileType() );
			projDocFileEntity.setFolderPath( upload_path );
			projDocFileEntity.setStatus( 1 );
			projDocFileEntity.setVersion( "1" );
			//projDocFileEntity.setProjectId( materialPODeliveryDocketTO.getMaterialProjDtlEntity().getProjId().getProjectId() );	
			projDocFileEntity.setCreatedBy( userMstrEntity );
			projDocFileEntity.setUpdatedBy( userMstrEntity );
			ProjMstrEntity projMstrEntity = new ProjMstrEntity();
			projMstrEntity.setProjectId(projectId);
			projDocFileEntity.setProjectId( projMstrEntity );
			resProjDocFileEntity = projDocFileRepository.save( projDocFileEntity );
			
			entity.setProjDocFileEntity( resProjDocFileEntity );
        }

        return entity;
    }

}
