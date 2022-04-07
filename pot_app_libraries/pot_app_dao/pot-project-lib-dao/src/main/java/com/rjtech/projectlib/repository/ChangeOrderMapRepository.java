package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.projectlib.model.ChangeOrderMapEntity;

@Repository
public interface ChangeOrderMapRepository extends JpaRepository<ChangeOrderMapEntity, Long> {
	
	@Query("SELECT COMAP FROM ChangeOrderMapEntity COMAP WHERE COMAP.coMstrId.id=:coMstrId AND COMAP.manpowerId IS NOT NULL")
	List<ChangeOrderMapEntity> findCoManpowerDetails( @Param("coMstrId") Long coMstrId );
		
	@Query("SELECT COMAP FROM ChangeOrderMapEntity COMAP WHERE COMAP.coMstrId.id=:coMstrId AND COMAP.plantId IS NOT NULL")
	List<ChangeOrderMapEntity> findCoPlantDetails( @Param("coMstrId") Long coMstrId );
	
}
