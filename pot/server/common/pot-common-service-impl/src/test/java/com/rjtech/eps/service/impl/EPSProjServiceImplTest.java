package com.rjtech.eps.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserProjectsEntity;
import com.rjtech.eps.model.ProjMstrEntity;
import com.rjtech.projectlib.resp.ProjectResp;
import com.rjtech.user.repository.CommonUserProjectsRepository;
import com.rjtech.user.req.UserProjGetReq;

public class EPSProjServiceImplTest  {
	
	private EPSProjServiceImpl epsProjServiceImpl;
	
	@Mock
	private CommonUserProjectsRepository userProjectsRepository;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		epsProjServiceImpl = new EPSProjServiceImpl();
		epsProjServiceImpl.setUserProjectsRepository(userProjectsRepository);
	}
	
	@Test
	public void testGetEpsUserProjects() {
		UserProjGetReq userProjGetReq = new UserProjGetReq();
		userProjGetReq.setUserId(123L);
		userProjGetReq.setStatus(1);
		ProjMstrEntity projMstrEntity = getProjMstrEntity();
		List<UserProjectsEntity> userProjectsEntities = new ArrayList<UserProjectsEntity>();
		UserProjectsEntity userProjectsEntity = new UserProjectsEntity();
		userProjectsEntity.setId(1l);
		userProjectsEntity.setProjectMstrEntity(projMstrEntity);
		userProjectsEntities.add(userProjectsEntity);
		UserProjectsEntity userProjectsEntityOne = new UserProjectsEntity();
		userProjectsEntityOne.setId(2l);
		userProjectsEntityOne.setProjectMstrEntity(projMstrEntity.getParentProjectMstrEntity().getChildEntities().get(1));
		userProjectsEntities.add(userProjectsEntityOne);
		UserProjectsEntity userProjectsEntityTwo = new UserProjectsEntity();
		userProjectsEntityTwo.setId(3l);
		userProjectsEntityTwo.setProjectMstrEntity(projMstrEntity.getParentProjectMstrEntity().getParentProjectMstrEntity().getChildEntities().get(1));
		userProjectsEntities.add(userProjectsEntityTwo);
		when(userProjectsRepository.findUserProjects(123L, 1)).thenReturn(userProjectsEntities);
		ProjectResp projectResp = epsProjServiceImpl.getEPSUserProjects(userProjGetReq);
		Assert.assertTrue(projectResp.getEpsProjs().size() == 1);
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getParentId() == null);
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().size() == 2);
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().get(0).getProjName().equals("SubParent"));
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().get(1).isProj());
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().get(0).getChildProjs().size() == 2);
	}
	
	@Test
	public void testGetEpsUserProjectsShuffled() {
		UserProjGetReq userProjGetReq = new UserProjGetReq();
		userProjGetReq.setUserId(123L);
		userProjGetReq.setStatus(1);
		ProjMstrEntity projMstrEntity = getProjMstrEntity();
		List<UserProjectsEntity> userProjectsEntities = new ArrayList<UserProjectsEntity>();
		UserProjectsEntity userProjectsEntityTwo = new UserProjectsEntity();
		userProjectsEntityTwo.setId(3l);
		userProjectsEntityTwo.setProjectMstrEntity(projMstrEntity.getParentProjectMstrEntity().getParentProjectMstrEntity().getChildEntities().get(1));
		userProjectsEntities.add(userProjectsEntityTwo);
		UserProjectsEntity userProjectsEntityOne = new UserProjectsEntity();
		userProjectsEntityOne.setId(2l);
		userProjectsEntityOne.setProjectMstrEntity(projMstrEntity.getParentProjectMstrEntity().getChildEntities().get(1));
		userProjectsEntities.add(userProjectsEntityOne);
		UserProjectsEntity userProjectsEntity = new UserProjectsEntity();
		userProjectsEntity.setId(1l);
		userProjectsEntity.setProjectMstrEntity(projMstrEntity);
		userProjectsEntities.add(userProjectsEntity);
		when(userProjectsRepository.findUserProjects(123L, 1)).thenReturn(userProjectsEntities);
		ProjectResp projectResp = epsProjServiceImpl.getEPSUserProjects(userProjGetReq);
		Assert.assertTrue(projectResp.getEpsProjs().size() == 1);
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getParentId() == null);
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().size() == 2);
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().get(0).getProjName().equals("SuperParentChild"));
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().get(0).isProj());
		Assert.assertTrue(projectResp.getEpsProjs().get(0).getChildProjs().get(1).getChildProjs().size() == 2);
	}
	
	private ProjMstrEntity getProjMstrEntity() {
		ClientRegEntity clientRegEntity = new ClientRegEntity();
		clientRegEntity.setClientId(1L);
		
		ProjMstrEntity projMstrEntity = new ProjMstrEntity();
		projMstrEntity.setProjectId(1L);
		projMstrEntity.setProjName("proj1");
		projMstrEntity.setProj(true);
		projMstrEntity.setClientId(clientRegEntity);
		projMstrEntity.setStatus(new Integer(1));
		
		ProjMstrEntity subParent = new ProjMstrEntity();
		subParent.setProjectId(2L);
		subParent.setProjName("SubParent");
		subParent.getChildEntities().add(projMstrEntity);
		subParent.setClientId(clientRegEntity);
		subParent.setStatus(new Integer(1));
		
		ProjMstrEntity superParent = new ProjMstrEntity();
		superParent.setProjectId(3L);
		superParent.setProjName("SuperParent");
		superParent.getChildEntities().add(subParent);
		superParent.setClientId(clientRegEntity);
		superParent.setStatus(new Integer(1));
		
		ProjMstrEntity superParentChild = new ProjMstrEntity();
		superParentChild.setProj(true);
		superParentChild.setParentProjectMstrEntity(superParent);
		superParentChild.setProjectId(4L);
		superParentChild.setProjName("SuperParentChild");
		superParentChild.setClientId(clientRegEntity);
		superParentChild.setStatus(new Integer(1));
		
		superParent.getChildEntities().add(superParentChild);
		
		ProjMstrEntity subParentChild = new ProjMstrEntity();
		subParentChild.setProj(true);
		subParentChild.setParentProjectMstrEntity(subParent);
		subParentChild.setProjectId(5L);
		subParentChild.setProjName("subParentChild");
		subParentChild.setClientId(clientRegEntity);
		subParentChild.setStatus(new Integer(1));
		
		subParent.getChildEntities().add(subParentChild);
		
		subParent.setParentProjectMstrEntity(superParent);
		projMstrEntity.setParentProjectMstrEntity(subParent);
		return projMstrEntity;
	}
}
