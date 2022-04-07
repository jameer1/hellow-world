package com.rjtech.eps.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.rjtech.user.repository.ProjGeneralUserRepositoryCopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.EPSProjectTO;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.model.ProjStatusDatesEntityCopy;
import com.rjtech.common.model.UserProjectsEntity;
import com.rjtech.common.repository.ClientRegRepository;
import com.rjtech.common.repository.ProjGeneralRepositoryCopyCopy;
import com.rjtech.common.repository.UserProjectsRepositoryProjCopy;
import com.rjtech.common.service.exception.RJSException;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.eps.service.handler.EPSProjServiceHandler;
import com.rjtech.eps.service.handler.ProjGeneralHandler;
import com.rjtech.projectlib.repository.EPSProjRepository;
import com.rjtech.projectlib.repository.ProjStatusDatesRepositoryCopy;
import com.rjtech.projectlib.req.ProjDeleteReq;
import com.rjtech.projectlib.req.ProjFilterReq;
import com.rjtech.projectlib.req.ProjGetReq;
import com.rjtech.projectlib.req.ProjSaveReq;
import com.rjtech.projectlib.resp.ProjectResp;
import com.rjtech.projectlib.service.EPSProjService;
import com.rjtech.rjs.appuser.utils.AppUserUtils;
import com.rjtech.rjs.core.annotations.RJSService;
import com.rjtech.user.dto.UserProjDetailsTO;
import com.rjtech.user.repository.CommonUserProjectsRepository;
import com.rjtech.user.repository.ProjGeneralUserRepository;
import com.rjtech.user.req.UserProjGetReq;
import com.rjtech.user.resp.UserProjResp;
import com.rjtech.common.repository.UserProjectsRepositoryProjCopy;
import com.rjtech.projectlib.repository.ProjStatusDatesRepositoryCopy;
//import com.rjtech.projectlib.model.ProjStatusDatesEntityCopy;
import com.rjtech.common.model.ProjStatusDatesEntityCopy;
import com.rjtech.projectlib.repository.ResourceAssignmentDataRepositoryCopy1;
import com.rjtech.projectlib.model.ResourceAssignmentDataEntityCopy;
import com.rjtech.projectlib.model.ResourceAssignmentDataValueEntityCopy;
import com.rjtech.projectlib.repository.ProjSowTotalActualRepositoryCopy;

@Service(value = "epsProjService")
@RJSService(modulecode = "epsProjService")
@Transactional
public class EPSProjServiceImpl implements EPSProjService {
	
	@Autowired
	private ProjSowTotalActualRepositoryCopy projSowTotalActualRepository;
	
	@Autowired
	private ResourceAssignmentDataRepositoryCopy1 resourceAssignmentDataRepository;

    @Autowired
    private EPSProjRepository epsProjRepository;

    @Autowired
    private CommonUserProjectsRepository userProjectsRepository;

    @Autowired
    private ProjGeneralUserRepositoryCopy projGeneralUserRepositoryCopy;
    
    @Autowired
    private ClientRegRepository clientRegRepository;

    @Autowired
    private CommonUserProjectsRepository commonUserProjectsRepository;
    
    @Autowired
    private ProjGeneralRepositoryCopyCopy projGeneralRepository;
    
    
    @Autowired
    private UserProjectsRepositoryProjCopy userProjectsRepositoryProjCopy;
    
    @Autowired
    private ProjStatusDatesRepositoryCopy projStatusDatesRepositoryCopy;

    public EPSProjRepository getEpsProjRepository() {
        return epsProjRepository;
    }

    public void setEpsProjRepository(EPSProjRepository epsProjRepository) {
        this.epsProjRepository = epsProjRepository;
    }

    public CommonUserProjectsRepository getUserProjectsRepository() {
        return userProjectsRepository;
    }

    public void setUserProjectsRepository(CommonUserProjectsRepository userProjectsRepository) {
        this.userProjectsRepository = userProjectsRepository;
    }

    public ClientRegRepository getClientRegRepository() {
        return clientRegRepository;
    }

    public void setClientRegRepository(ClientRegRepository clientRegRepository) {
        this.clientRegRepository = clientRegRepository;
    }

    public ProjGeneralUserRepositoryCopy getProjGeneralUserRepositoryCopy() {
        return projGeneralUserRepositoryCopy;
    }

    public void setProjGeneralUserRepositoryCopy(ProjGeneralUserRepositoryCopy projGeneralUserRepositoryCopy) {
        this.projGeneralUserRepositoryCopy = projGeneralUserRepositoryCopy;
    }

    public ProjectResp getEPSProjects(ProjGetReq projGetReq) {
    	 ProjStatusDatesEntityCopy projStatusDatesEntityCopy = new ProjStatusDatesEntityCopy();
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findEPSProjects(AppUserUtils.getClientId(),
                projGetReq.getStatus());
        List<Long> projectsid = userProjectsRepositoryProjCopy.getAllUserProjects();
        ProjectResp projectResp = new ProjectResp();
        for (ProjMstrEntity projectMstrEntity : projMstrEntities) {
        	//System.out.println("projectMstrEntity.getProjectId()" + projectMstrEntity.getProjectId());
        	//List<ProjGeneralMstrEntity> projGeneralMstrEntites = projGeneralRepository
    		//		.findProjGenerals(projectMstrEntity.getProjectId(), projectMstrEntity.getStatus());
        	//for (ProjGeneralMstrEntity projGeneralMstrEntity : projGeneralMstrEntites) {
        	//	projectResp.setProjGeneralMstrTO(ProjGeneralHandler.convertEntityToPOJO(projGeneralMstrEntity));
        	//}
            projectResp.getEpsProjs().add(EPSProjServiceHandler.populateProjectTO(projectMstrEntity, true, projGeneralRepository));
        }
        for(EPSProjectTO ePSProjectTOindex: projectResp.getEpsProjs()) {
        	for(EPSProjectTO ePSProjectTOindex1: ePSProjectTOindex.getChildProjs()) {
        	//System.out.println("@@@@@@#####$$$$$%%%%%%%!!!!!!!!!   " + projectsid.indexOf(ePSProjectTOindex1.getProjId()));
			if (projectsid.indexOf(ePSProjectTOindex1.getProjId()) != -1) {
				ePSProjectTOindex1.setProjectAssigned(true);
			} else {
				ePSProjectTOindex1.setProjectAssigned(false);
			}
        	}
		}
		
		for (EPSProjectTO ePSProjectTO : projectResp.getEpsProjs()) {

			projStatusDatesEntityCopy = projStatusDatesRepositoryCopy.findProjStatusDatesById(ePSProjectTO.getProjId(),
					1);
			if (projStatusDatesEntityCopy != null) {
				
				if (projStatusDatesEntityCopy.getStartDate() != null) {
					ePSProjectTO.setStartDate(CommonUtil.convertDateToString(projStatusDatesEntityCopy.getStartDate()));
				} else {
					ePSProjectTO.setStartDate("N/A");
				}
				if (projStatusDatesEntityCopy.getFinishDate() != null) {
					ePSProjectTO
							.setFinishDate(CommonUtil.convertDateToString(projStatusDatesEntityCopy.getFinishDate()));
				} else {
					ePSProjectTO.setFinishDate("N/A");
				}
				if (projStatusDatesEntityCopy.getCurrentPhase() != null) {
					ePSProjectTO.setProjCurrentPhase(projStatusDatesEntityCopy.getCurrentPhase());
				} else {
					ePSProjectTO.setProjCurrentPhase("N/A");
				}
			}
			if (ePSProjectTO.getChildProjs().size() > 0) {
				for (EPSProjectTO ePSProjectTOChild : ePSProjectTO.getChildProjs()) {
					Long earnedValue = getTotalEarnedValue(ePSProjectTOChild.getProjId());
					List<ResourceAssignmentDataEntityCopy> resourceAssignmentDataEntities = resourceAssignmentDataRepository.findByBaseline(ePSProjectTOChild.getProjId());
					BigDecimal plannedValue = BigDecimal.ZERO;
					for (ResourceAssignmentDataEntityCopy resourceAssignmentDataEntity : resourceAssignmentDataEntities) {
						if (resourceAssignmentDataEntity.getReferenceType().contentEquals("POT_COST")) {
							for (ResourceAssignmentDataValueEntityCopy resourceAssignmentDataValueEntity : resourceAssignmentDataEntity.getResourceAssignmentDataValueEntities()) {
								if (resourceAssignmentDataValueEntity.getForecastDate().before(new Date())) {
									plannedValue = plannedValue.add(BigDecimal.valueOf(resourceAssignmentDataValueEntity.getBudgetUnits()));
								}
							}
						}
					}
					projStatusDatesEntityCopy = projStatusDatesRepositoryCopy
							.findProjStatusDatesById(ePSProjectTOChild.getProjId(), projGetReq.getStatus());
					ePSProjectTOChild.setPlannedValue(plannedValue);
					ePSProjectTOChild.setEarnedValue(earnedValue);
					if (projStatusDatesEntityCopy != null) {
						if (projStatusDatesEntityCopy.getStartDate() != null) {
							ePSProjectTOChild.setStartDate(
									CommonUtil.convertDateToString(projStatusDatesEntityCopy.getStartDate()));
						} else {
							ePSProjectTOChild.setStartDate("N/A");
						}
						if (projStatusDatesEntityCopy.getFinishDate() != null) {
							ePSProjectTOChild.setFinishDate(
									CommonUtil.convertDateToString(projStatusDatesEntityCopy.getFinishDate()));
						} else {
							ePSProjectTOChild.setFinishDate("N/A");
						}
						if (projStatusDatesEntityCopy.getCurrentPhase() != null) {
							ePSProjectTOChild.setProjCurrentPhase(projStatusDatesEntityCopy.getCurrentPhase());
						} else {
							ePSProjectTOChild.setProjCurrentPhase("N/A");
						}
					}
				}
			}
		}

        /*
        ObjectMapper om = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = om.writeValueAsString(projectResp);
        } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("=======================================");
        System.out.println(jsonString);
        System.out.println("=======================================");
		*/
        return projectResp;
    }
    
	private Long getTotalEarnedValue(long projId) {
		Map<Long, BigDecimal> earnedValues = getEarnedValueForCostId(projId);
		Long earnedValue = 0L;
		for (BigDecimal earned : earnedValues.values()) {
			earnedValue += earned.longValue();
		}
		return earnedValue;
	}

	private Map<Long, BigDecimal> getEarnedValueForCostId(long projId) {
//		log.info("getEarnedValueForCostId method");
		Map<Long, BigDecimal> earnedValues = new HashMap<>();
		List<Object[]> resp = projSowTotalActualRepository.getEarnedValuePerCostCode(projId);
		for (Object[] object : resp) {
			if ((BigDecimal) object[2] != null && object[1] != null) {
				BigDecimal rate = ((BigDecimal) object[2]).multiply(BigDecimal.valueOf((Double) object[1]));
				earnedValues.merge((Long) object[0], rate, BigDecimal::add);
			}
		}
	//	log.info("earnedValues " + earnedValues);
		return earnedValues;
	}

    public ProjectResp getProjEpsName(ProjGetReq projGetReq) {
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findEPSProjectName(projGetReq.getProjId());
        ProjectResp projectResp = new ProjectResp();
        for (ProjMstrEntity projectMstrEntity : projMstrEntities) {
            projectResp.getEpsProjs().add(EPSProjServiceHandler.populateProjectTO(projectMstrEntity, true, projGeneralRepository));
        }
        return projectResp;
    }

    public ProjectResp getEPSProjectsById(ProjGetReq projGetReq) {
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findEPSProjectsById(AppUserUtils.getClientId(),
                projGetReq.getProjId(), projGetReq.getStatus());
        ProjectResp projectResp = new ProjectResp();
        for (ProjMstrEntity projectMstrEntity : projMstrEntities) {
            projectResp.getEpsProjs().add(EPSProjServiceHandler.populateProjectTO(projectMstrEntity, true, projGeneralRepository));
        }
        return projectResp;
    }

    public ProjectResp getProjectEps(ProjGetReq projGetReq) {
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findProjectEps(AppUserUtils.getClientId(),
                StatusCodes.ACTIVE.getValue());
        ProjectResp projectResp = new ProjectResp();
        for (ProjMstrEntity projectMstrEntity : projMstrEntities) {
            projectResp.getEpsProjs().add(EPSProjServiceHandler.populateProjectTO(projectMstrEntity, false, projGeneralRepository));
        }
        return projectResp;
    }

    public ProjectResp getProjects(ProjGetReq projGetReq) {
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findProjects(AppUserUtils.getClientId(),
                StatusCodes.ACTIVE.getValue());
        ProjectResp projectResp = new ProjectResp();
        for (ProjMstrEntity projectMstrEntity : projMstrEntities) {
            projectResp.getEpsProjs().add(EPSProjServiceHandler.populateProjectTO(projectMstrEntity, false, projGeneralRepository));
        }
        return projectResp;
    }

    public ProjectResp getProjectsById(ProjFilterReq projFilterReq) {
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findEPSProjectsById(AppUserUtils.getClientId(),
                projFilterReq.getProjId(), projFilterReq.getStatus());
        ProjectResp projectResp = new ProjectResp();
        for (ProjMstrEntity projectMstrEntity : projMstrEntities) {
            projectResp.getEpsProjs().add(EPSProjServiceHandler.populateProjectTO(projectMstrEntity, false, projGeneralRepository));
        }
        return projectResp;
    }

    public void saveProject(ProjSaveReq projSaveReq) {
        List<ProjMstrEntity> projMstrEntities = EPSProjServiceHandler.populateEntitisFromPOJO(projSaveReq,
                clientRegRepository);
        epsProjRepository.save(projMstrEntities);
    }

    public void deleteProject(ProjDeleteReq projDeleteReq) {
        for (Long projId : projDeleteReq.getProjectIds()) {
            epsProjRepository.delete(projId);
        }
    }

    public UserProjResp getAllUserProjects(UserProjGetReq userProjGetReq) {
        UserProjResp userProjResp = new UserProjResp();
        Long userId = null;
        if (CommonUtil.isNonBlankLong(userProjGetReq.getUserId())) {
            userId = userProjGetReq.getUserId();
        } else {
            userId = AppUserUtils.getUserId();
        }
        List<UserProjectsEntity> userEntityList = userProjectsRepository.findAllUserProjects(userId);
        if (CommonUtil.isListHasData(userEntityList)) {
            for (UserProjectsEntity userProjectsEntity : userEntityList) {
                UserProjDetailsTO userProjDetailsTO = EPSProjServiceHandler.populateUserProjectTO(userProjectsEntity);
                userProjResp.getUserProjDetailsTOs().add(userProjDetailsTO);
            }
        }
        return userProjResp;
    }

    public ProjectResp getEPSUserProjects(UserProjGetReq userProjGetReq) {

        ProjectResp projectResp = new ProjectResp();
        List<EPSProjectTO> epsProjectTOList = getProjectsWithParents(getUserProjectParents(userProjGetReq));
        projectResp.getEpsProjs().addAll(epsProjectTOList);
        return projectResp;
    }

    private List<EPSProjectTO> getUserProjectParents(UserProjGetReq userProjGetReq) {
        Long userId = null;
        if (CommonUtil.isNonBlankLong(userProjGetReq.getUserId())) {
            userId = userProjGetReq.getUserId();
        } else {
            userId = AppUserUtils.getUserId();
        }
        System.out.println("EPSProjServiceImpl:getUserProjectParents:ContractType:"+userProjGetReq.getContractType());

        System.out.println("EPSProjServiceImpl:getEPSUserProjects:userProjGetReq");
        System.out.println(userProjGetReq);
        System.out.println("getClientCode : "+userProjGetReq.getClientCode());
        System.out.println("getContractType : "+userProjGetReq.getContractType());
        System.out.println("getUserId : "+userProjGetReq.getUserId());
        //System.out.println("getProjId : "+userProjGetReq.getProjId());
        List<Long> projectContractTypeList=new ArrayList<Long>();
        System.out.println("userId : "+userId+" : Status : "+userProjGetReq.getStatus()+" : ContractType : "+userProjGetReq.getContractType());
        if (CommonUtil.isNotBlankStr(userProjGetReq.getContractType()))
        {
            //projectContractTypeList  = projGeneralUserRepositoryCopy.findUserProjIds(userId, userProjGetReq.getStatus(),userProjGetReq.getContractType());
            projectContractTypeList  = projGeneralUserRepositoryCopy.findUserProjIds(userProjGetReq.getStatus(),userProjGetReq.getContractType());
            if(projectContractTypeList!=null && projectContractTypeList.size()>0) {
                System.out.println("projectContractTypeList:"+projectContractTypeList.size());
                System.out.println(projectContractTypeList);
            }

        }

        List<UserProjectsEntity> userEntityList = userProjectsRepository.findUserProjects(userId,
                userProjGetReq.getStatus());
        List<EPSProjectTO> parents = new ArrayList<EPSProjectTO>();
        List<EPSProjectTO> contractTypeProj = new ArrayList<EPSProjectTO>();
        List<Long> finalProjectContractTypeList = projectContractTypeList;
        if(finalProjectContractTypeList!=null && finalProjectContractTypeList.size()>0) {
            System.out.println("finalProjectContractTypeList:"+finalProjectContractTypeList);
            System.out.println(finalProjectContractTypeList);
        }
        userEntityList.forEach((userMstrEntity) -> {
            ProjMstrEntity actualProj = userMstrEntity.getProjectMstrEntity();
            EPSProjectTO actualProjTo = EPSProjServiceHandler.populateUserProjectTO(actualProj, false);
            ProjMstrEntity projMstrEntity = actualProj.getParentProjectMstrEntity();
            EPSProjectTO parentProjectTo = EPSProjServiceHandler.populateUserProjectTO(projMstrEntity, false);

            if(finalProjectContractTypeList !=null && finalProjectContractTypeList.size()>0)
            {
                System.out.println("actualProjTo.getProjId :"+actualProjTo.getProjId());
                actualProjTo.setEnableContractType(finalProjectContractTypeList.contains(actualProjTo.getProjId()));
            }
            parentProjectTo.getChildProjs().add(actualProjTo);
            while (projMstrEntity.getParentProjectMstrEntity() != null) {
                projMstrEntity = projMstrEntity.getParentProjectMstrEntity();
                EPSProjectTO tempProjectTo = EPSProjServiceHandler.populateUserProjectTO(projMstrEntity, false);
                tempProjectTo.getChildProjs().add(parentProjectTo);
                parentProjectTo = tempProjectTo;
            }
            if(actualProjTo.isEnableContractType() && userProjGetReq.getContractType() != null ) {
            	contractTypeProj.add(parentProjectTo);
            } else{
            	parents.add(parentProjectTo);
            }
        });
	        if(userProjGetReq.getContractType() != null) {
	        	return contractTypeProj;
	        } else {
	        	return parents;
	        }
    }

    private List<EPSProjectTO> getProjectsWithParents(List<EPSProjectTO> parents) {
        List<EPSProjectTO> projects = new ArrayList<EPSProjectTO>();
        // Iterating over all the parents and fetching only projects assigned to user
        for (int i = 0; i < parents.size(); i++) {
            EPSProjectTO ePSProjectTO = parents.get(i);
            // If the projects list is empty, we add the first project.
            if (projects.size() == 0) {
                projects.add(ePSProjectTO);
                continue;
            }
            boolean existingProj = false;
            // Iterating over the projects which are added already to the list to see, 
            // if the new projects parent is available
            for (EPSProjectTO project : projects) {
                // condition will be successful if the parent is already available
                if (project.getProjId() == ePSProjectTO.getProjId()) {
                    existingProj = true;
                    List<EPSProjectTO> availableParents = new ArrayList<EPSProjectTO>();
                    // getting the innermost child which is the project from the parent.
                    EPSProjectTO child = ePSProjectTO;
                    while (child.getChildProjs().size() > 0) {
                        child = child.getChildProjs().get(0);
                        if (child.getChildProjs().size() != 0)
                            availableParents.add(child);
                    }

                    // If the project is a direct child of root project, we add it to the childs list of root parent.
                    if (child.getParentId() == project.getProjId()) {
                        project.getChildProjs().add(child);
                        break;
                    }

                    Map<Long, EPSProjectTO> epsProjectsMap = new HashMap<Long, EPSProjectTO>();
                    epsProjectsMap.put(project.getProjId(), project);
                    getParentsMap(project, epsProjectsMap);

                    boolean found = false;
                    do {
                        EPSProjectTO parent = epsProjectsMap.get(child.getParentId());
                        if (parent != null) {
                            parent.getChildProjs().add(child);
                            found = true;
                        } else {
                            child = getParentById(availableParents, child.getParentId());
                        }
                    } while (!found);

                }
            }

            if (!existingProj)
                projects.add(ePSProjectTO);
        }
        return projects;
    }

    private EPSProjectTO getParentById(List<EPSProjectTO> availableParents, Long parentId) {
        EPSProjectTO parent = null;
        for (EPSProjectTO parentFromList : availableParents) {
            if (parentFromList.getProjId() == parentId) {
                parent = parentFromList;
                return parent;
            }
        }
        return parent;
    }

    private void getParentsMap(EPSProjectTO project, Map<Long, EPSProjectTO> epsProjectsMap) {
        for (EPSProjectTO child : project.getChildProjs()) {
            if (!child.isProj()) {
                epsProjectsMap.put(child.getProjId(), child);
                if (child.getChildProjs().size() > 0) {
                    getParentsMap(child, epsProjectsMap);
                }
            }
        }
    }

    public UserProjResp getProjectsByUserId(UserProjGetReq userProjGetReq) {
        UserProjResp userProjResp = new UserProjResp();
        List<ProjMstrEntity> mstrEntities = epsProjRepository.findProjects(AppUserUtils.getClientId(),
                userProjGetReq.getStatus());
        UserProjDetailsTO userProjDetailsTO = null;
        Map<Long, UserProjectsEntity> usrerProjMap = getUsrProjs(userProjGetReq);
        UserProjectsEntity userProjectsEntity = null;
        for (ProjMstrEntity projMstrEntity : mstrEntities) {
            userProjDetailsTO = EPSProjServiceHandler.populateProjectTO(projMstrEntity);
            userProjDetailsTO.setUserId(userProjGetReq.getUserId());
            userProjectsEntity = usrerProjMap.get(userProjDetailsTO.getProjId());
            if (userProjectsEntity != null) {
                userProjDetailsTO.setId(userProjectsEntity.getId());
                if (StatusCodes.ACTIVE.getValue().equals(userProjectsEntity.getStatus())) {
                    userProjDetailsTO.setUsrProj(true);
                } else {
                    userProjDetailsTO.setUsrProj(false);
                }
            }
            userProjResp.getUserProjDetailsTOs().add(userProjDetailsTO);
        }
        return userProjResp;
    }

    private Map<Long, UserProjectsEntity> getUsrProjs(UserProjGetReq userProjGetReq) {
        List<UserProjectsEntity> userProjectsEntities = userProjectsRepository
                .findUserProjects(userProjGetReq.getUserId(), userProjGetReq.getStatus());
        Map<Long, UserProjectsEntity> usrerProjMap = new HashMap<Long, UserProjectsEntity>();
        for (UserProjectsEntity userProjectsEntity : userProjectsEntities) {
            usrerProjMap.put(userProjectsEntity.getProjectMstrEntity().getProjectId(), userProjectsEntity);
        }
        return usrerProjMap;
    }

    public void deactivateProjectEps(ProjDeleteReq projDeleteReq) {
        List<Long> assignedProjects = new ArrayList<>();
        for (Long projId : projDeleteReq.getProjectIds()) {
            if (commonUserProjectsRepository.getUsersCountForProject(projId) > 0) {
                assignedProjects.add(projId);
            }
        }
        if (assignedProjects.isEmpty())
            epsProjRepository.deactivateProjectEps(projDeleteReq.getProjectIds(), projDeleteReq.getStatus());
        else
            throw new RJSException(String.valueOf(HttpStatus.PRECONDITION_FAILED.value()), assignedProjects.toString());
    }

    public ProjectResp getEPSOnly(ProjGetReq projGetReq) {
        ProjectResp projectResp = new ProjectResp();
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findOnlyEPSProjects(projGetReq.getStatus(),
                AppUserUtils.getClientId());
        projectResp.getEpsProjs().addAll(EPSProjServiceHandler.populateEPS(projMstrEntities));
        return projectResp;
    }

    public Map<Long, LabelKeyTO> getUserProjects() {
        List<UserProjectsEntity> userProjectsEntities = userProjectsRepository
                .findUserProjectsByClient(AppUserUtils.getUserId(), AppUserUtils.getClientId(), 1);
        Map<Long, LabelKeyTO> labelKeyMap = new HashMap<Long, LabelKeyTO>();
        for (UserProjectsEntity userProjectsEntity : userProjectsEntities) {
            LabelKeyTO labelKeyTO = new LabelKeyTO();
            labelKeyTO.setId(userProjectsEntity.getProjectMstrEntity().getProjectId());
            labelKeyTO.setCode(userProjectsEntity.getProjectMstrEntity().getCode());
            labelKeyTO.setName(userProjectsEntity.getProjectMstrEntity().getProjName());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_EPSCODE_KEY,
                    userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity().getCode());
            labelKeyTO.getDisplayNamesMap().put(CommonConstants.PROJECT_PARENT_NAME_KEY,
                    userProjectsEntity.getProjectMstrEntity().getParentProjectMstrEntity().getProjName());
            labelKeyMap.put(labelKeyTO.getId(), labelKeyTO);
        }
        return labelKeyMap;
    }

    public List<Long> getUserProjIds() {
        return userProjectsRepository.findUserProjIds(AppUserUtils.getUserId(), ApplicationConstants.STATUS_ACTIVE);
    }

    public UserProjResp getAllProjects() {
        UserProjResp userProjResp = new UserProjResp();
        List<ProjMstrEntity> projMstrEntities = epsProjRepository.findProjects(AppUserUtils.getClientId(),
                StatusCodes.ACTIVE.getValue());
        for (ProjMstrEntity projectMstrEntity : projMstrEntities) {
            userProjResp.getUserProjDetailsTOs().add(EPSProjServiceHandler.populateProjMstrTO(projectMstrEntity));
        }
        return userProjResp;

    }

    public void deactivateEps(ProjDeleteReq projDeleteReq) {
        List<Long> relatedProjs = new ArrayList<>();
        for (Long epsId : projDeleteReq.getProjectIds()) {
            if (epsProjRepository.getProjCountByEpsId(epsId) > 0) {
                relatedProjs.add(epsId);
            }
        }
        if (relatedProjs.isEmpty())
            epsProjRepository.deactivateProjectEps(projDeleteReq.getProjectIds(), projDeleteReq.getStatus());
        else
            throw new RJSException(String.valueOf(HttpStatus.PRECONDITION_FAILED.value()), relatedProjs.toString());
    }

}
