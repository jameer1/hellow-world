package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.model.ProjSORItemEntity;

//import com.rjtech.projectlib.model.ProjSORItemEntityCopy;

@Repository
public interface ProjSORItemRepository extends JpaRepository<ProjSORItemEntity, Long> {

	@Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projectId AND SOR.code=:sorCode")
	public ProjSORItemEntity findBy(@Param("projectId") Long projectId, @Param("sorCode") String sorCode);
	
	@Query("SELECT SOR FROM ProjSORItemEntity SOR WHERE SOR.projMstrEntity.projectId=:projId AND SOR.status=:status AND SOR.projSORItemEntity.id IS NULL ORDER BY SOR.code")
    public List<ProjSORItemEntity> findSORDetails(@Param("projId") Long projId, @Param("status") Integer status);

}
