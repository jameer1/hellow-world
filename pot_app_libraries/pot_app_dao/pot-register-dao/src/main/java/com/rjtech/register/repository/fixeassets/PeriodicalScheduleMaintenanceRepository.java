package com.rjtech.register.repository.fixeassets;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rjtech.register.fixedassets.model.PeriodicalScheduleMaintenanceDtlEntity;
import com.rjtech.register.fixedassets.model.STCORRReturnsDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface PeriodicalScheduleMaintenanceRepository
        extends RegisterBaseRepository<PeriodicalScheduleMaintenanceDtlEntity, Long> {
    
    @Query("SELECT FGV FROM PeriodicalScheduleMaintenanceDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid=:fixedAssetid AND FGV.status=:status")
    public List<PeriodicalScheduleMaintenanceDtlEntity> findAllPeriodicalScheduleMaintenance(@Param("fixedAssetid") Long fixedAssetid,
            @Param("status") Integer status);

    @Modifying
    @Query("DELETE from PeriodicalScheduleMaintenanceDtlEntity rv  where rv.id in :periodicalScheduleMaintenanceIds")
    void periodicalScheduleMaintenanceDelete(
            @Param("periodicalScheduleMaintenanceIds") List<Long> periodicalScheduleMaintenanceIds);
    
    @Modifying
    @Query("UPDATE PeriodicalScheduleMaintenanceDtlEntity FGA  SET FGA.status=:status  where FGA.id in :periodicalIds")
    public void deactivateperiodicalScheduleMaintenanceRecord(@Param("periodicalIds") List<Long> periodicalIds, @Param("status") Integer status);

    @Query("SELECT FGV FROM PeriodicalScheduleMaintenanceDtlEntity FGV  WHERE FGV.fixedAssetsRegisterDtlEntity.fixedAssetid in (:fixedassetids) AND FGV.status=:status")
    public List<PeriodicalScheduleMaintenanceDtlEntity> findAllProjectPeriodical(@Param("fixedassetids") List<Integer> fixedassetids,
            @Param("status") Integer status);

}
