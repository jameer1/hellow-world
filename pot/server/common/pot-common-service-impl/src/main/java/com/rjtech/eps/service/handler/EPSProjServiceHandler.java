package com.rjtech.eps.service.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.dto.ProjGeneralMstrTO;
import com.rjtech.common.model.UserProjectsEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.ProjGeneralRepositoryCopyCopy;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.eps.model.ProjGeneralMstrEntityCopy;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.req.ProjSaveReq;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.user.dto.UserProjDetailsTO;

public class EPSProjServiceHandler {

    public static List<EPSProjectTO> populateEPS(List<ProjMstrEntity> projMstrEntities) {
        List<EPSProjectTO> epsProjects = new ArrayList<EPSProjectTO>();
        for (ProjMstrEntity projMstrEntity : projMstrEntities) {
            epsProjects.add(recursiveEps(projMstrEntity, new EPSProjectTO()));
        }
        return epsProjects;
    }

    private static EPSProjectTO recursiveEps(ProjMstrEntity projMstrEntity, EPSProjectTO epsProjectTo) {
        epsProjectTo = convertEPSEntityToPOJO(projMstrEntity);
        List<ProjMstrEntity> childEntites = projMstrEntity.getChildEntities();
        if (!childEntites.isEmpty()) {
            for (ProjMstrEntity child : childEntites) {
                if (child.getStatus() == 1 && !child.isProj()) {
                    if (!child.getChildEntities().isEmpty())
                        epsProjectTo.getChildProjs().add(recursiveEps(child, new EPSProjectTO()));
                    else
                        epsProjectTo.getChildProjs().add(convertEPSEntityToPOJO(child));
                }
            }
        }
        return epsProjectTo;
    }

    private static EPSProjectTO convertEPSEntityToPOJO(ProjMstrEntity projMstrEntity) {
        EPSProjectTO epsProjectTO = new EPSProjectTO();
        epsProjectTO.setProjId(projMstrEntity.getProjectId());
        epsProjectTO.setClientId(projMstrEntity.getClientId().getClientId());
        epsProjectTO.setProjCode(projMstrEntity.getCode());
        epsProjectTO.setProjName(projMstrEntity.getProjName());
        epsProjectTO.setProj(projMstrEntity.isProj());
        ProjMstrEntity paretnProjMstrEntity = projMstrEntity.getParentProjectMstrEntity();
        if (paretnProjMstrEntity != null) {
            epsProjectTO.setParentId(paretnProjMstrEntity.getProjectId());
            epsProjectTO.setParentName(paretnProjMstrEntity.getProjName());
        }
        epsProjectTO.setStatus(projMstrEntity.getStatus());
        return epsProjectTO;
    }

    public static EPSProjectTO populateUserProjectTO(ProjMstrEntity projectMstrEntity, boolean addChild) {
        EPSProjectTO epsProjectTO = new EPSProjectTO();
        List<EPSProjectTO> childProjs = new ArrayList<EPSProjectTO>();
        epsProjectTO.setProjId(projectMstrEntity.getProjectId());
        epsProjectTO.setClientId(projectMstrEntity.getClientId().getClientId());
        epsProjectTO.setProjCode(projectMstrEntity.getCode());
        epsProjectTO.setProjName(projectMstrEntity.getProjName());
        epsProjectTO.setProj(projectMstrEntity.isProj());
        ProjMstrEntity paretnProjMstrEntity = projectMstrEntity.getParentProjectMstrEntity();
        if (paretnProjMstrEntity != null) {
            epsProjectTO.setParentId(paretnProjMstrEntity.getProjectId());
            epsProjectTO.setParentName(paretnProjMstrEntity.getProjName());
            epsProjectTO.setParentProjectTO(populateUserProjectTO(paretnProjMstrEntity, addChild));
        }

        epsProjectTO.setAssignedStatus(projectMstrEntity.isAssignedStatus());
        epsProjectTO.setStatus(projectMstrEntity.getStatus());

        if (addChild) {
            addChildUserProjects(projectMstrEntity, epsProjectTO, childProjs, addChild);
            epsProjectTO.setChildProjs(childProjs);
        }
        return epsProjectTO;
    }

    private static void addChildUserProjects(ProjMstrEntity projectMstrEntity, EPSProjectTO projectTO,
            List<EPSProjectTO> childProjs, boolean addChild) {
        if (projectMstrEntity.getParentProjectMstrEntity() == null) {
            projectTO.setProj(false);
        } else {
            projectTO.setParentId(projectMstrEntity.getParentProjectMstrEntity().getProjectId());
        }
        for (ProjMstrEntity childProjMstr : projectMstrEntity.getChildEntities()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                childProjs.add(populateUserProjectTO(childProjMstr, addChild));
            }
        }
    }

    public static EPSProjectTO populateProjectTO(ProjMstrEntity projectMstrEntity, boolean addChild, ProjGeneralRepositoryCopyCopy projGeneralRepository) {
        EPSProjectTO epsProjectTO = new EPSProjectTO();

        List<EPSProjectTO> childProjs = new ArrayList<>();
        epsProjectTO.setProjId(projectMstrEntity.getProjectId());
        //System.out.println(" ########### projectMstrEntity.getProjectId() " + projectMstrEntity.getProjectId());
        epsProjectTO.setClientId(projectMstrEntity.getClientId().getClientId());
        epsProjectTO.setProjCode(projectMstrEntity.getCode());
        epsProjectTO.setProjName(projectMstrEntity.getProjName());
        epsProjectTO.setProj(projectMstrEntity.isProj());
        ProjMstrEntity paretnProjMstrEntity = projectMstrEntity.getParentProjectMstrEntity();
        if (paretnProjMstrEntity != null) {
            epsProjectTO.setParentId(paretnProjMstrEntity.getProjectId());
            epsProjectTO.setParentName(paretnProjMstrEntity.getProjName());
            epsProjectTO.setParentCode(paretnProjMstrEntity.getCode());
        }
        
        if (projectMstrEntity.isProj()) {
        List<ProjGeneralMstrEntityCopy> projGeneralMstrEntites = projGeneralRepository
				.findProjGenerals(projectMstrEntity.getProjectId(), projectMstrEntity.getStatus());
     //       System.out.println("projGeneralMstrEntites123 "+projGeneralMstrEntites.size());
	    	for (ProjGeneralMstrEntityCopy projGeneralMstrEntity : projGeneralMstrEntites) {
	    		epsProjectTO.setProjGeneralMstrTO(new ProjGeneralMstrTO());
	    		if (projGeneralMstrEntity.getCountryName() != null) {
	    			epsProjectTO.getProjGeneralMstrTO().setCountryName(projGeneralMstrEntity.getCountryName());
	    		}
	    		if (projGeneralMstrEntity.getProvisionName() != null) {
	    			epsProjectTO.getProjGeneralMstrTO().setProvisionName(projGeneralMstrEntity.getProvisionName());
	    		}
	    		if (projGeneralMstrEntity.getRespManager() != null) {
	    			epsProjectTO.getProjGeneralMstrTO().getUserLabelKeyTO().setName(projGeneralMstrEntity.getRespManager().getDisplayName());
	    		}
	    		if(projGeneralMstrEntity.getCompanyMstrEntity() != null) {
	    			epsProjectTO.getProjGeneralMstrTO().getCompanyTO().setCmpName(projGeneralMstrEntity.getCompanyMstrEntity().getName());
	    		}
	    		if(projGeneralMstrEntity.getContractType() != null) {
	    			epsProjectTO.getProjGeneralMstrTO().setContractType(projGeneralMstrEntity.getContractType());
	    		}
	    		if(projGeneralMstrEntity.getProfitCentreEntity() != null) {
	    			epsProjectTO.getProjGeneralMstrTO().getProfitCentreTO().setName(projGeneralMstrEntity.getProfitCentreEntity().getName());
	    		}
				if(projGeneralMstrEntity.getFinanceCentre() != null) {
					epsProjectTO.getProjGeneralMstrTO().setFinanceCentreId(projGeneralMstrEntity.getFinanceCentre()); 
				}
				 
	    	}
        }
        epsProjectTO.setAssignedStatus(projectMstrEntity.isAssignedStatus());
        epsProjectTO.setStatus(projectMstrEntity.getStatus());

        if (addChild) {
            addChildProjects(projectMstrEntity, epsProjectTO, childProjs, addChild, projGeneralRepository);
            epsProjectTO.setChildProjs(childProjs);
        }
        return epsProjectTO;
    }

    private static void addChildProjects(ProjMstrEntity projectMstrEntity, EPSProjectTO projectTO,
            List<EPSProjectTO> childProjs, boolean addChild, ProjGeneralRepositoryCopyCopy projGeneralRepository) {
        if (projectMstrEntity.getParentProjectMstrEntity() == null) {
            projectTO.setProj(false);
        } else {
            projectTO.setParentId(projectMstrEntity.getParentProjectMstrEntity().getProjectId());
        }
        for (ProjMstrEntity childProjMstr : projectMstrEntity.getChildEntities()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                childProjs.add(populateProjectTO(childProjMstr, addChild, projGeneralRepository));
            }
        }
    }

    public static EPSProjectTO populateUserProjectTO(ProjMstrEntity projectMstrEntity,
            Map<Long, Boolean> userProjectsMap) {
        EPSProjectTO epsProjectTO = new EPSProjectTO();
        List<EPSProjectTO> childProjs = new ArrayList<EPSProjectTO>();
        epsProjectTO.setProjId(projectMstrEntity.getProjectId());
        epsProjectTO.setClientId(projectMstrEntity.getClientId().getClientId());
        epsProjectTO.setProjCode(projectMstrEntity.getCode());
        epsProjectTO.setProjName(projectMstrEntity.getProjName());
        epsProjectTO.setProj(projectMstrEntity.isProj());

        ProjMstrEntity paretnProjMstrEntity = projectMstrEntity.getParentProjectMstrEntity();
        if (paretnProjMstrEntity != null) {
            epsProjectTO.setParentId(paretnProjMstrEntity.getProjectId());
            epsProjectTO.setParentName(paretnProjMstrEntity.getProjName());
        }
        epsProjectTO.setAssignedStatus(projectMstrEntity.isAssignedStatus());
        epsProjectTO.setStatus(projectMstrEntity.getStatus());

        addChildUserProjects(projectMstrEntity, epsProjectTO, childProjs, userProjectsMap);
        epsProjectTO.setChildProjs(childProjs);
        return epsProjectTO;
    }

    private static void addChildUserProjects(ProjMstrEntity projectMstrEntity, EPSProjectTO projectTO,
            List<EPSProjectTO> childProjs, Map<Long, Boolean> userProjectsMap) {
        projectTO.setExpand(true);
        if (projectMstrEntity.getParentProjectMstrEntity() == null) {
            projectTO.setProj(false);
        } else {
            projectTO.setParentId(projectMstrEntity.getParentProjectMstrEntity().getProjectId());
        }
        for (ProjMstrEntity childProjMstr : projectMstrEntity.getChildEntities()) {
            if (StatusCodes.ACTIVE.getValue().intValue() == childProjMstr.getStatus().intValue()) {
                if (childProjMstr.isProj()) {
                    if (userProjectsMap.get(childProjMstr.getProjectId()) != null) {
                        childProjs.add(populateUserProjectTO(childProjMstr, userProjectsMap));
                    }
                } else {
                    childProjs.add(populateUserProjectTO(childProjMstr, userProjectsMap));
                }
            }
        }
    }

    public static UserProjDetailsTO populateProjectTO(ProjMstrEntity projectMstrEntity) {
        UserProjDetailsTO userProjDetailsTO = new UserProjDetailsTO();
        userProjDetailsTO.setProjId(projectMstrEntity.getProjectId());
        userProjDetailsTO.setClientId(projectMstrEntity.getClientId().getClientId());
        userProjDetailsTO.setProjCode(projectMstrEntity.getProjName());
        userProjDetailsTO.setProjName(projectMstrEntity.getCode());
        if (projectMstrEntity.isProj()) {
            userProjDetailsTO.setProj(true);
        } else {
            userProjDetailsTO.setProj(false);
        }

        ProjMstrEntity paretnProjMstrEntity = projectMstrEntity.getParentProjectMstrEntity();
        if (paretnProjMstrEntity != null) {
            userProjDetailsTO.setParentId(paretnProjMstrEntity.getProjectId());
            userProjDetailsTO.setParentName(paretnProjMstrEntity.getProjName());
        }
        userProjDetailsTO.setStatus(projectMstrEntity.getStatus());
        return userProjDetailsTO;
    }

    public static List<ProjMstrEntity> populateEntitisFromPOJO(ProjSaveReq projSaveReq,
            ClientRegRepository clientRegRepository) {
        List<ProjMstrEntity> entities = new ArrayList<ProjMstrEntity>();
        ProjMstrEntity entity = null;
        for (EPSProjectTO epsProjectTO : projSaveReq.getProjs()) {
            entity = new ProjMstrEntity();
            converProjPOJOToEntity(entity, epsProjectTO, clientRegRepository);
            entities.add(entity);
        }
        return entities;
    }

    private static ProjMstrEntity converProjPOJOToEntity(ProjMstrEntity projectMstrEntity, EPSProjectTO projectTO,
            ClientRegRepository clientRegRepository) {
        if (CommonUtil.isNonBlankLong(projectTO.getProjId())) {
            projectMstrEntity.setProjectId(projectTO.getProjId());
        }
        projectMstrEntity.setCode(projectTO.getProjCode());
        projectMstrEntity.setAssignedStatus(projectTO.isAssignedStatus());
        if (CommonUtil.isNonBlankLong(projectTO.getParentId())) {
            ProjMstrEntity parentMstrEntity = new ProjMstrEntity();
            parentMstrEntity.setProjectId(projectTO.getParentId());
            projectMstrEntity.setParentProjectMstrEntity(parentMstrEntity);
        }

        Long clientId = projectTO.getClientId();
        if (CommonUtil.isNonBlankLong(clientId)) {
            projectMstrEntity.setClientId(clientRegRepository.findOne(clientId));
        } else {
            projectMstrEntity.setClientId(clientRegRepository.findOne(AppUserUtils.getClientId()));
        }
        projectMstrEntity.setProjName(projectTO.getProjName());
        projectMstrEntity.setProj(projectTO.isProj());
        projectMstrEntity.setStatus(projectTO.getStatus());
        ProjMstrEntity childEntity = null;
        for (EPSProjectTO childTO : projectTO.getChildProjs()) {
            childEntity = new ProjMstrEntity();
            childEntity.setParentProjectMstrEntity(projectMstrEntity);
            projectMstrEntity.getChildEntities().add(converProjPOJOToEntity(childEntity, childTO, clientRegRepository));
        }
        return projectMstrEntity;
    }

    public static UserProjDetailsTO populateUserProjectTO(UserProjectsEntity userProjectsEntity) {
        UserProjDetailsTO userProjDetailsTO = null;
        userProjDetailsTO = new UserProjDetailsTO();
        if (CommonUtil.objectNotNull(userProjectsEntity.getProjectMstrEntity())) {
            userProjDetailsTO.setId(userProjectsEntity.getId());
            userProjDetailsTO.setUserId(userProjectsEntity.getUserId().getUserId());
            userProjDetailsTO.setProjId(userProjectsEntity.getProjectMstrEntity().getProjectId());
            userProjDetailsTO.setClientId(userProjectsEntity.getProjectMstrEntity().getClientId().getClientId());
            userProjDetailsTO.setProjCode(userProjectsEntity.getProjectMstrEntity().getCode());
            userProjDetailsTO.setProjName(userProjectsEntity.getProjectMstrEntity().getProjName());
            if (CommonUtil.isNonBlankInteger(userProjectsEntity.getStatus())
                    && StatusCodes.ACTIVE.getValue().equals(userProjectsEntity.getStatus())) {
                userProjDetailsTO.setUsrProj(true);
            } else {
                userProjDetailsTO.setUsrProj(false);
            }
            if (CommonUtil.objectNotNull(userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity())) {
                userProjDetailsTO.setParentId(
                        userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity().getProjectId());
                userProjDetailsTO.setParentName(
                        userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity().getProjName());
                userProjDetailsTO.setParentEPSCode(
                        userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity().getCode());
            }
            userProjDetailsTO.setStatus(userProjectsEntity.getStatus());
        }
        return userProjDetailsTO;
    }

    public static LabelKeyTO getLableKeyTO(UserProjectsEntity userProjectsEntity) {
        LabelKeyTO labelKeyTO = null;
        labelKeyTO = new LabelKeyTO();
        if (CommonUtil.objectNotNull(userProjectsEntity.getProjectMstrEntity())) {
            labelKeyTO.setId(userProjectsEntity.getProjectMstrEntity().getProjectId());
            labelKeyTO.setName(userProjectsEntity.getProjectMstrEntity().getProjName());
            labelKeyTO.setCode(userProjectsEntity.getProjectMstrEntity().getCode());
            if (CommonUtil.objectNotNull(userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity())) {
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_EPSCODE_KEY,
                        userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity().getProjName());
                labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_NAME_KEY,
                        userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity().getProjName());
            }
        }
        return labelKeyTO;
    }

    public static UserProjDetailsTO populateProjMstrTO(ProjMstrEntity projectMstrEntity) {
        UserProjDetailsTO userProjDetailsTO = null;
        userProjDetailsTO = new UserProjDetailsTO();
        userProjDetailsTO.setProjId(projectMstrEntity.getProjectId());
        userProjDetailsTO.setClientId(projectMstrEntity.getClientId().getClientId());
        userProjDetailsTO.setProjCode(projectMstrEntity.getCode());
        userProjDetailsTO.setProjName(projectMstrEntity.getProjName());
        if (CommonUtil.objectNotNull(projectMstrEntity.getParentProjectMstrEntity())) {
            userProjDetailsTO.setParentId(
                    projectMstrEntity.getParentProjectMstrEntity().getParentProjectMstrEntity().getProjectId());
            userProjDetailsTO.setParentName(projectMstrEntity.getParentProjectMstrEntity().getProjName());
            userProjDetailsTO.setParentEPSCode(projectMstrEntity.getParentProjectMstrEntity().getCode());
        }
        userProjDetailsTO.setStatus(projectMstrEntity.getStatus());

        return userProjDetailsTO;
    }
}
