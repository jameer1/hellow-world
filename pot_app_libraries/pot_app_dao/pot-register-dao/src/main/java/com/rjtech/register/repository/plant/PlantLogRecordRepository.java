package com.rjtech.register.repository.plant;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.plant.model.PlantLogRecordEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PlantLogRecordRepository extends RegisterBaseRepository<PlantLogRecordEntity, Long> {

  //  @Query("SELECT T FROM PlantLogRecordEntity T WHERE T.plantId.id = :plantId AND T.status=:status  AND T.createdOn between :fromDate and :toDate ORDER BY createdOn DESC") old query 
	@Query("SELECT T FROM PlantLogRecordEntity T WHERE T.plantId.id = :plantId AND T.status=:status  AND T.fromDate between :fromDate and :toDate ORDER BY createdOn DESC")
    public List<PlantLogRecordEntity> findPlantLogRecords(@Param("plantId") Long plantId,
            @Param("status") Integer status, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Modifying
    @Query("UPDATE PlantLogRecordEntity T  SET T.status=:status  where T.id in :ids ")
    void deactivatePlantLogrecords(@Param("ids") List<Long> ids, @Param("status") Integer status);

    @Query("select MAX(T.id) from PlantLogRecordEntity T where T.plantRegProjEntity.id =:plantProjId ")
    public Long getMaxLogRecordIdByPlantProjId(@Param("plantProjId") Long plantProjId);
}
