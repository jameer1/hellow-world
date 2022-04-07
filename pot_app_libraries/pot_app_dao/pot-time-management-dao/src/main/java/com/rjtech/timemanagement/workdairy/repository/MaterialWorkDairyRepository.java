package com.rjtech.timemanagement.workdairy.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntity;
//import com.rjtech.register.material.model.MaterialProjDocketSchItemsEntityCopy;
import com.rjtech.timemanagement.workdairy.model.WorkDairyMaterialDtlEntity;

@Repository
public interface MaterialWorkDairyRepository extends JpaRepository<WorkDairyMaterialDtlEntity, Long> {

    @Query("SELECT T FROM WorkDairyMaterialDtlEntity T  WHERE T.workDairyEntity.id=:workDairyId AND T.status=:status")
    List<WorkDairyMaterialDtlEntity> findWorkDairyMaterialDetails(@Param("workDairyId") Long workDairyId,
            @Param("status") Integer status);

    @Query("SELECT SUM(T.consumptionQty) FROM WorkDairyMaterialDtlEntity T  WHERE T.projDocketSchId = :workDairyMaterialSchItem")
    BigDecimal getDocketConsumption(@Param("workDairyMaterialSchItem") MaterialProjDocketSchItemsEntity workDairyMaterialSchItem);
    
    @Query("SELECT T FROM WorkDairyMaterialDtlEntity T WHERE T.projDocketSchId = :docketSchId AND "
            + "T.workDairyEntity.workDairyDate > :workDairyDate ORDER BY T.workDairyEntity.workDairyDate")
    List<WorkDairyMaterialDtlEntity> findWorkDairyMaterialDetails(@Param("docketSchId") MaterialProjDocketSchItemsEntity docketSchId,
            @Param("workDairyDate") Date workDairyDate);
    
    @Query("SELECT T FROM WorkDairyMaterialDtlEntity T WHERE T.projDocketSchId = :docketSchId ORDER BY T.closingStock desc, T.updatedOn desc")
    List<WorkDairyMaterialDtlEntity> findWorkDairyMaterialDetails(@Param("docketSchId") MaterialProjDocketSchItemsEntity docketSchId);
}
