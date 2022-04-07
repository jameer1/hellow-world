package com.rjtech.document.service.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.document.dto.ProjDocFolderTO;
import com.rjtech.document.model.ProjDocFolderEntity;
import com.rjtech.document.model.ProjDocFolderPermissionEntity;
import com.rjtech.document.repository.ProjDocFolderPermissionRepository;
import com.rjtech.document.repository.ProjDocFolderRepository;
import com.rjtech.document.service.impl.ProjDocumentServiceImpl;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.rjs.appuser.utils.AppUserUtils;

public class ProjDocFolderHandler_backup {
	private static final Logger log = LoggerFactory.getLogger(ProjDocFolderHandler.class);

    public static ProjDocFolderTO convertEntityToPOJOdocument(ProjDocFolderEntity projDocFolderEntity, boolean addChild,Long projId,
    		ProjDocFolderRepository projDocFolderRepository, ProjDocFolderPermissionRepository projDocFolderPermissionRepository, Long userid, Integer status) {
    	
        ProjDocFolderTO projDocFolderTO = null;
        projDocFolderTO = new ProjDocFolderTO();
        List<ProjDocFolderTO> childProjs = new ArrayList<ProjDocFolderTO>();
      //  ProjDocFolderPermissionEntity projDocFolderPermissionEntity=projDocFolderEntity.p
      
       
        projDocFolderTO.setId(projDocFolderEntity.getId());
        projDocFolderTO.setFolderType( projDocFolderEntity.getFolderType() );
        if (CommonUtil.objectNotNull(projDocFolderEntity.getProjId())) {
            projDocFolderTO.setProjId(projDocFolderEntity.getProjId().getProjectId());
        }
        ProjDocFolderEntity projDocParentEntity = projDocFolderEntity.getProjDocFolderEntity();
        if(null!=projDocParentEntity) {
            projDocFolderTO.setParentId(projDocParentEntity.getId()); 
        }
        projDocFolderTO.setName(projDocFolderEntity.getName());
        if (CommonUtil.objectNotNull(projDocFolderEntity.getProjDocFolderEntity())) {
            projDocFolderTO.setParentName(projDocFolderEntity.getProjDocFolderEntity().getName());
        }
        projDocFolderTO.setStatus(projDocFolderEntity.getStatus());
        //ProjDocFolderPermissionEntity findProjDocFolderPermissionsAndUser(@Param("folderId") Long folderId, @Param("userId") Long userId, @Param("status") Integer status);       
        
        if (addChild) {
        	System.out.println("addChild block:"+projDocFolderEntity.getName());     	
            addChildProjectsdoc(projDocFolderEntity, projDocFolderTO, childProjs, addChild, projId, projDocFolderRepository,projDocFolderPermissionRepository,userid,status);
            projDocFolderTO.setChildProjDocFolderTOs(childProjs);
        }
        return projDocFolderTO;
    }
    
    private static void addChildProjectsdoc(ProjDocFolderEntity projDocFolderEntity,
            ProjDocFolderTO projDocFolderTO, List<ProjDocFolderTO> childProjs, boolean addChild,Long projId, ProjDocFolderRepository projDocFolderRepository, ProjDocFolderPermissionRepository projDocFolderPermissionRepository,Long userid,Integer status) {
        List<ProjDocFolderPermissionEntity> permissions = projDocFolderPermissionRepository.findProjDocFolderPermissionsOfUseronly(userid,status);
        
        if(projDocFolderEntity.getName().equalsIgnoreCase("Other Documents")) {
        	System.out.println("other documents if condition");
        	List<ProjDocFolderEntity> projDocSubFolderEntities = projDocFolderRepository.findProjDocSubFolders(projId,projDocFolderEntity.getId(),1);
        	if (projDocFolderEntity.getProjDocFolderEntity() == null) {
                for (ProjDocFolderEntity childProjMstr : projDocSubFolderEntities) {
                	System.out.println(childProjMstr.getName());
                	System.out.println(childProjMstr.getFolderPermissions().size());
                    if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                        childProjs.add(convertEntityToPOJOdocument(childProjMstr, addChild,projId,projDocFolderRepository,projDocFolderPermissionRepository,userid,status));
                    }
                }
            } else {
            	System.out.println("else condition of if condition other documents");
                projDocFolderTO.setParentId(projDocFolderEntity.getProjDocFolderEntity().getId());
                for(ProjDocFolderPermissionEntity permissions1 : permissions)
                {
	                for (ProjDocFolderEntity childProjMstr : projDocSubFolderEntities) {
	                	if( permissions1.getFolder().getId()==childProjMstr.getId() ) {
		                    if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
		                        childProjs.add(convertEntityToPOJOdocument(childProjMstr, addChild,projId,projDocFolderRepository,projDocFolderPermissionRepository,userid,status));
		                    }
	                	}
	                }
                }
            }
            
        }else {
        	  
            if (projDocFolderEntity.getProjDocFolderEntity() == null) {
                for (ProjDocFolderEntity childProjMstr : projDocFolderEntity.getChildEntities()) {
                	System.out.println(childProjMstr.getFolderPermissions().size());
                    if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                        childProjs.add(convertEntityToPOJOdocument(childProjMstr, addChild,projId,projDocFolderRepository,projDocFolderPermissionRepository,userid,status));
                    }
                }
            } else {
                projDocFolderTO.setParentId(projDocFolderEntity.getProjDocFolderEntity().getId());
                Long projectId = projDocFolderTO.getProjId();
                System.out.println("project id:"+projectId);
                for(ProjDocFolderPermissionEntity permissions1 : permissions)
                {
	                for (ProjDocFolderEntity childProjMstr : projDocFolderEntity.getChildEntities()) {	                	
	                	log.info("child pfro"+childProjMstr.getId()+" name:"+childProjMstr.getName());	                
	                	if( permissions1.getFolder().getId()==childProjMstr.getId() ) {
	                		log.info(" testt");
		                    if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
		                        childProjs.add(convertEntityToPOJOdocument(childProjMstr, addChild,projId,projDocFolderRepository,projDocFolderPermissionRepository,userid,status));
		                    }
	                	}	                	
	                } 
                }
            }
        }
    }

    public static ProjDocFolderTO convertEntityToPOJO(ProjDocFolderEntity projDocFolderEntity, boolean addChild,Long projId,
    		ProjDocFolderRepository projDocFolderRepository) {
    	
        ProjDocFolderTO projDocFolderTO = null;
        projDocFolderTO = new ProjDocFolderTO();
        List<ProjDocFolderTO> childProjs = new ArrayList<ProjDocFolderTO>();
        //System.out.println(projDocFolderEntity);
       
        projDocFolderTO.setId(projDocFolderEntity.getId());   
        if (CommonUtil.objectNotNull(projDocFolderEntity.getProjId())) {
            projDocFolderTO.setProjId(projDocFolderEntity.getProjId().getProjectId());
        }
        ProjDocFolderEntity projDocParentEntity = projDocFolderEntity.getProjDocFolderEntity();
        if(null!=projDocParentEntity) {
            projDocFolderTO.setParentId(projDocParentEntity.getId()); 
        }
        projDocFolderTO.setName(projDocFolderEntity.getName());
        if (CommonUtil.objectNotNull(projDocFolderEntity.getProjDocFolderEntity())) {
            projDocFolderTO.setParentName(projDocFolderEntity.getProjDocFolderEntity().getName());
        }
        projDocFolderTO.setStatus(projDocFolderEntity.getStatus());
        projDocFolderTO.setFolderType(projDocFolderEntity.getFolderType());
      
        if (addChild) {
        	if( projDocFolderEntity.getName() != null )
        	{
        		addChildProjects(projDocFolderEntity, projDocFolderTO, childProjs, addChild, projId, projDocFolderRepository);
                projDocFolderTO.setChildProjDocFolderTOs(childProjs);
        	}
        }
        return projDocFolderTO;
    }
    
    private static void addChildProjects(ProjDocFolderEntity projDocFolderEntity,
            ProjDocFolderTO projDocFolderTO, List<ProjDocFolderTO> childProjs, boolean addChild,Long projId, ProjDocFolderRepository projDocFolderRepository) {
       
        
        if(projDocFolderEntity.getName().equalsIgnoreCase("Other Documents")) {
        	List<ProjDocFolderEntity> projDocSubFolderEntities = projDocFolderRepository.findProjDocSubFolders(projId,projDocFolderEntity.getId(),1);
      
            if (projDocFolderEntity.getProjDocFolderEntity() == null) {
                for (ProjDocFolderEntity childProjMstr : projDocSubFolderEntities) {
                    if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                        childProjs.add(convertEntityToPOJO(childProjMstr, addChild,projId,projDocFolderRepository));
                    }
                }
            } else {
                projDocFolderTO.setParentId(projDocFolderEntity.getProjDocFolderEntity().getId());
                for (ProjDocFolderEntity childProjMstr : projDocSubFolderEntities) {
                         if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                        childProjs.add(convertEntityToPOJO(childProjMstr, addChild,projId,projDocFolderRepository));
                    }
                }
            }
            
        }else {
        	  
            if (projDocFolderEntity.getProjDocFolderEntity() == null) {
                for (ProjDocFolderEntity childProjMstr : projDocFolderEntity.getChildEntities()) {
                    if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                        childProjs.add(convertEntityToPOJO(childProjMstr, addChild,projId,projDocFolderRepository));
                    }
                }
            } else {
                projDocFolderTO.setParentId(projDocFolderEntity.getProjDocFolderEntity().getId());      
                for (ProjDocFolderEntity childProjMstr : projDocFolderEntity.getChildEntities()) {
                        if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                        childProjs.add(convertEntityToPOJO(childProjMstr, addChild,projId,projDocFolderRepository));
                    }
                } 
            }
        }
    }

   
    public static List<ProjDocFolderEntity> populateEntitiesFromPOJO(List<ProjDocFolderTO> projDocFolderTOs,
            ProjDocFolderRepository projDocFolderRepository, EPSProjRepository epsRepository) {        
        List<ProjDocFolderEntity> projFolderEntities = new ArrayList<ProjDocFolderEntity>();       
        ProjDocFolderEntity entity = null;

        for (ProjDocFolderTO projDocFolderTO : projDocFolderTOs) {
            entity = new ProjDocFolderEntity();
            convertPOJOToEntity(entity, projDocFolderTO, projDocFolderRepository, epsRepository);
            projFolderEntities.add(entity);
        }
        return projFolderEntities;
    }

    public static ProjDocFolderEntity convertPOJOToEntity(ProjDocFolderEntity projDocFolderEntity,
            ProjDocFolderTO projDocFolderTO, ProjDocFolderRepository projDocFolderRepository,
            EPSProjRepository epsRepository) {
    	System.out.println("convertPOJOToEntity function");
        ProjMstrEntity projMstrEntity =null;
        if (CommonUtil.isNonBlankLong(projDocFolderTO.getId())) {
            projDocFolderEntity.setId(projDocFolderTO.getId());
        }
        projDocFolderEntity.setName(projDocFolderTO.getName());
        if (CommonUtil.isNonBlankLong(projDocFolderTO.getParentId())) {
            ProjDocFolderEntity parentEntity = projDocFolderRepository.findOne(projDocFolderTO.getParentId());
            projDocFolderEntity.setProjDocFolderEntity(parentEntity);
        }
        projDocFolderEntity.setStatus(projDocFolderTO.getStatus());
        String docName = projDocFolderTO.getName();
        Long projectId = projDocFolderTO.getProjId();
        if (docName.equalsIgnoreCase("Other Documents")) {
        	System.out.println("other documents block");
            projMstrEntity = null;
            projDocFolderEntity.setFolderType( "CUSTOM" );
        }
        else if(null != projectId){
            projMstrEntity = epsRepository.findOne(projectId);
        }
        if (null != projMstrEntity) {
            projDocFolderEntity.setProjId(projMstrEntity);
        }
        
        ProjDocFolderEntity childEntity = null;
        for (ProjDocFolderTO childTO : projDocFolderTO.getChildProjDocFolderTOs()) {
            childEntity = new ProjDocFolderEntity();
            childEntity.setProjDocFolderEntity(projDocFolderEntity);
            projDocFolderEntity.getChildEntities()
            .add(convertPOJOToEntity(childEntity, childTO, projDocFolderRepository, epsRepository));
        }
        return projDocFolderEntity;
    }

}
