package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.ProjectTemplatesEntity;

@Repository
public interface ProjectTemplatesRepository extends JpaRepository<ProjectTemplatesEntity, Long> {	
	@Modifying
    @Query("UPDATE ProjectTemplatesEntity PTEMPLS SET PTEMPLS.templateJson=:templateJson where PTEMPLS.templateId=:templateId")
    void updateTemplateJsonById( @Param("templateId") Long templateId , @Param("templateJson") String templateJson );
	
	@Query("SELECT PTEMPLS FROM ProjectTemplatesEntity PTEMPLS WHERE PTEMPLS.templateCategoryId.categoryId=:templateCategoryId")
	List<ProjectTemplatesEntity> findTemplatesByCategory( @Param("templateCategoryId") Long templateCategoryId );
	
	@Query("SELECT PTEMPLS FROM ProjectTemplatesEntity PTEMPLS WHERE PTEMPLS.projId.projectId=:projId AND PTEMPLS.crmId.clientId=:crmId")
	List<ProjectTemplatesEntity> findProjectTemplatesByProjId( @Param("projId") Long projId , @Param("crmId") Long crmId );
	
	@Modifying
    @Query("UPDATE ProjectTemplatesEntity PTEMPLS SET PTEMPLS.status=:status where PTEMPLS.templateId=:templateId")
	void updateTemplateStatusById( @Param("templateId") Long templateId , @Param("status") String status );
	
	@Query("SELECT PTEMPLS FROM ProjectTemplatesEntity PTEMPLS WHERE PTEMPLS.templateId=:templateId")
	ProjectTemplatesEntity findTemplatesByTemplateId( @Param("templateId") Long templateId );
	
	@Modifying
    @Query("UPDATE ProjectTemplatesEntity PTEMPLS SET PTEMPLS.internalApprovalStatus=:approvalStatus,PTEMPLS.isInternalApproved=:isApproved,PTEMPLS.versionStatus=:versionStatus,PTEMPLS.isExternalApprovalRequired=:isExternalApprovalRequired,PTEMPLS.status=:status,PTEMPLS.internalApprovedBy.userId=:approvedBy where PTEMPLS.templateId=:templateId")
	void updateTemplateInternalApprovalStatusById( @Param("templateId") Long templateId , @Param("approvalStatus") String approvalStatus , @Param("isApproved") Character isApproved ,  @Param("versionStatus") Integer version_status , @Param("isExternalApprovalRequired") Character isExternalApprovalRequired , @Param("status") String status , @Param("approvedBy") Long approvedBy );
	
	@Modifying
    @Query("UPDATE ProjectTemplatesEntity PTEMPLS SET PTEMPLS.externalApprovalStatus=:approvalStatus,PTEMPLS.status=:status,PTEMPLS.versionStatus=:versionStatus,PTEMPLS.isExternalApproved=:isApproved,PTEMPLS.externalApprovedBy.userId=:approvedBy where PTEMPLS.templateId=:templateId")
	void updateTemplateExternalApprovalStatusById( @Param("templateId") Long templateId , @Param("approvalStatus") String approvalStatus , @Param("status") String status , @Param("versionStatus") Integer version_status , @Param("isApproved") Character isApproved , @Param("approvedBy") Long approvedBy );
	
	@Query("SELECT MAX(PTEMPLS.templateId) FROM ProjectTemplatesEntity PTEMPLS")
	Long findLastInsertedTemplateId();
	
	@Modifying
	@Query("UPDATE ProjectTemplatesEntity PTEMPLS SET PTEMPLS.formsCount=:formsCnt where PTEMPLS.templateId=:templateId")
	void updateProjectTemplatesFormsCnt( @Param("templateId") Long templateId , @Param("formsCnt") Integer formsCnt );
}
