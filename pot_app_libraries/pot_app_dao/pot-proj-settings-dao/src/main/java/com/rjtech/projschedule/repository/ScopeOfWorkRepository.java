package com.rjtech.projschedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projectlib.model.ProjSOWItemEntity;

//import com.rjtech.projectlib.model.ProjSOWItemEntityCopy;

public interface ScopeOfWorkRepository extends JpaRepository<ProjSOWItemEntity, Long>{

	@Query("SELECT T.tangibleClassificationEntity.id, T.tangibleClassificationEntity.code, T.tangibleClassificationEntity.name, T.tangibleClassificationEntity.measurmentMstrEntity.name, SUM(T.projSOEItemEntity.quantity), SUM(T.revisedQty), SUM(T.actualQty), MIN(T.startDate), MAX(T.finishDate) "
			+ "FROM ProjSOWItemEntity T WHERE "
			+ "T.projMstrEntity.projectId=:projectId "
			+ "AND T.tangibleClassificationEntity.id IS NOT NULL "
			+ "GROUP BY T.tangibleClassificationEntity.id, T.tangibleClassificationEntity.code, T.tangibleClassificationEntity.name, T.tangibleClassificationEntity.measurmentMstrEntity.name "
			+ "ORDER BY T.tangibleClassificationEntity.code")
	public List<Object[]> findAllTangible(@Param("projectId") Long projectId);
	
	@Query("SELECT T.projSORItemEntity.id, T.projSORItemEntity.code, T.projSORItemEntity.name, SUM(COALESCE(T.projSOEItemEntity.quantity,0)), SUM(COALESCE(T.revisedQty,0)), SUM(COALESCE(T.actualQty,0)), MIN(T.startDate), MAX(T.finishDate) "
			+ "FROM ProjSOWItemEntity T WHERE "
			+ "T.projMstrEntity.projectId=:projectId "
			+ "AND T.projSORItemEntity.id IS NOT NULL "
			+ "GROUP BY T.projSORItemEntity.id, T.projSORItemEntity.code, T.projSORItemEntity.name "
			+ "ORDER BY T.projSORItemEntity.code")
	public List<Object[]> findAllScheduleOfRate(@Param("projectId") Long projectId);
	
	@Query("SELECT T FROM ProjSOWItemEntity T "
			+ "WHERE T.projMstrEntity.projectId=:projId "
			+ "AND T.tangibleClassificationEntity.id=:resId")
	public List<ProjSOWItemEntity> findByTangible(@Param("projId") Long projId, @Param("resId") Long resId);
	
	@Query("SELECT T FROM ProjSOWItemEntity T "
			+ "WHERE T.projMstrEntity.projectId=:projId "
			+ "AND T.projSORItemEntity.id=:resId")
	public List<ProjSOWItemEntity> findBySOR(@Param("projId") Long projId, @Param("resId") Long resId);
}
