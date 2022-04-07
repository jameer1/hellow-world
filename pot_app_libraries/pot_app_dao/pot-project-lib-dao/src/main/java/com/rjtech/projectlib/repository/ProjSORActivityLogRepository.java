package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.projectlib.model.ProjSORActivityLogEntity;

@Repository
public interface ProjSORActivityLogRepository extends JpaRepository<ProjSORActivityLogEntity, Long> {
	@Query("SELECT SORACTIVTY FROM ProjSORActivityLogEntity SORACTIVTY WHERE SORACTIVTY.projSORItemEntity.id in :sorIds")
    public List<ProjSORActivityLogEntity> getActivityDetailsBySorIds( @Param("sorIds") List<Long> sorIds );
}
