package com.rjtech.document.service.handler;

import com.rjtech.common.repository.LoginRepository;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.document.dto.ProjDocFolderPermissionTO;
import com.rjtech.document.model.ProjDocFolderPermissionEntity;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.eps.model.ProjMstrEntity;

public class ProjDocFolderPermissionHandler {

    public static ProjDocFolderPermissionTO convertEntityToPOJO(
            ProjDocFolderPermissionEntity projDocFolderPermissionEntity) {

        ProjDocFolderPermissionTO projDocFolderPermissionTO = new ProjDocFolderPermissionTO();
        projDocFolderPermissionTO.setId(projDocFolderPermissionEntity.getId());
        projDocFolderPermissionTO.setUserId(projDocFolderPermissionEntity.getUserId().getUserId());
        projDocFolderPermissionTO.setFolderId(projDocFolderPermissionEntity.getFolder().getId());
        projDocFolderPermissionTO.setReadAccess(projDocFolderPermissionEntity.isReadAccess());
        projDocFolderPermissionTO.setWriteAccess(projDocFolderPermissionEntity.isWriteAccess());
        projDocFolderPermissionTO.setStatus(projDocFolderPermissionEntity.getStatus());
        Long projectId = 0L;
        if( projDocFolderPermissionEntity.getProjectEntity() != null )
        {
        	projectId = projDocFolderPermissionEntity.getProjectEntity().getProjectId();
        }
        projDocFolderPermissionTO.setProjId( projectId );
        return projDocFolderPermissionTO;
    }

    public static void convertPOJOToEntity(ProjDocFolderPermissionTO projDocFolderPermissionTO,
            ProjDocFolderRepository projDocFolderRepository, LoginRepository loginRepository,
            ProjDocFolderPermissionEntity projDocFolderPermissionEntity) {
        Long userId = projDocFolderPermissionTO.getUserId();
        System.out.println(projDocFolderPermissionTO);
        ProjMstrEntity projMstrEntity = new ProjMstrEntity();
        projMstrEntity.setProjectId( projDocFolderPermissionTO.getProjId() );
        if (userId != null) {
            projDocFolderPermissionEntity.setUserId(loginRepository.findOne(userId));
        }
        projDocFolderPermissionEntity
                .setFolder(projDocFolderRepository.findOne(projDocFolderPermissionTO.getFolderId()));
        projDocFolderPermissionEntity.setReadAccess(projDocFolderPermissionTO.isReadAccess());
        projDocFolderPermissionEntity.setWriteAccess(projDocFolderPermissionTO.isWriteAccess());
        projDocFolderPermissionEntity.setStatus(projDocFolderPermissionTO.getStatus());
        projDocFolderPermissionEntity.setProjectEntity( projMstrEntity );
        System.out.println("convertPOJOToEntity of ProjDocFolderPermissionHandler class");
        System.out.println(projDocFolderPermissionEntity);
    }

}
