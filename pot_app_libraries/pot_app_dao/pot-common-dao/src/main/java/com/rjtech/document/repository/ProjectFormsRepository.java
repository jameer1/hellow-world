package com.rjtech.document.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.document.model.ProjectFormsEntity;

@Repository
public interface ProjectFormsRepository extends JpaRepository<ProjectFormsEntity, Long> {
	
	@Query("SELECT PFORMS FROM ProjectFormsEntity PFORMS WHERE PFORMS.projectId.projectId=:projectId AND PFORMS.templateCategoryId.categoryId=:templateCategoryId AND PFORMS.formType=:formType AND PFORMS.projectTemplateId.templateId=:projectTemplateId")
	List<ProjectFormsEntity> findFormsByProject( @Param("projectId") Long projectId , @Param("templateCategoryId") Long templateCategoryId , @Param("formType") String formType , @Param("projectTemplateId") Long projectTemplateId );
	
	@Modifying
    @Query("UPDATE ProjectFormsEntity PFORMS SET PFORMS.formJson=:formJson, PFORMS.formVersion=:formVersion, PFORMS.status=:status  where PFORMS.formId=:formId")
	void updateProjFormById( @Param("formId") Long formId, @Param("formVersion") String formVersion,  @Param("formJson") String formJson, @Param("status") String status );
	
	@Query("SELECT PFORMS FROM ProjectFormsEntity PFORMS WHERE PFORMS.formId=:formId")
	ProjectFormsEntity findProjFormById( @Param("formId") Long formId );
	
}
