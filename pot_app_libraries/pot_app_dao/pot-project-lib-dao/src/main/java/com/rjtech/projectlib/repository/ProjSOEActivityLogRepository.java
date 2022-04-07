package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rjtech.projectlib.model.ProjSOEActivityLogEntity;

@Repository
public interface ProjSOEActivityLogRepository extends JpaRepository<ProjSOEActivityLogEntity, Long> {
	@Query("SELECT SOEACTIVTY FROM ProjSOEActivityLogEntity SOEACTIVTY WHERE SOEACTIVTY.projSOEItemEntity.id in :soeIds")
    public List<ProjSOEActivityLogEntity> getActivityDetailsBySoeIds( @Param("soeIds") List<Long> soeIds );
}
