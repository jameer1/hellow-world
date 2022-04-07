package com.rjtech.register.repository.fixeassets;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.fixedassets.model.OccupancyUtilisationRecordsDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface OccupancyUtilisationRecordsRepository
        extends RegisterBaseRepository<OccupancyUtilisationRecordsDtlEntity, Long> {

    @Query("SELECT SA FROM OccupancyUtilisationRecordsDtlEntity SA  WHERE SA.subAssetDtlEntity.subid=:subid ")
    List<OccupancyUtilisationRecordsDtlEntity> findOccupancyUtilisationRecords(@Param("subid") Long subid);

    @Modifying
    @Query("DELETE from OccupancyUtilisationRecordsDtlEntity rv  where rv.id in :occupancyIds")
    void occupancyUtilisationRecordsDelete(@Param("occupancyIds") List<Long> occupancyIds);

}
