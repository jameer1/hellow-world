package com.rjtech.register.repository.material;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.material.model.MaterialProjDocketEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface MaterialProjDocketRepository extends RegisterBaseRepository<MaterialProjDocketEntity, Long> {

    @Query("SELECT T FROM MaterialProjDocketEntity T WHERE T.toProjId.projectId=:projId AND T.sourceType=:sourceType "
            + "AND (T.docketStatus = null OR T.docketStatus!='C') "
            + "AND T.status=:status AND T.docketDate <= :workDairyDate")
    public List<MaterialProjDocketEntity> findMaterialProjDocketsByType(@Param("projId") Long projId,
            @Param("sourceType") String sourceType, @Param("status") Integer status, 
            @Param("workDairyDate") Date workDairyDate);

    @Query("SELECT T FROM MaterialProjDocketEntity T WHERE T.toProjId.projectId=:projId AND T.sourceType=:sourceType AND T.originStockCodeCategory !='Stockpile' AND T.apprStatus=:apprStatus "
            + "AND (T.docketStatus = null OR T.docketStatus!='C') "
            + "AND T.status=:status AND T.docketDate <= :workDairyDate")
    public List<MaterialProjDocketEntity> findMaterialGeneratedProjDockets(@Param("projId") Long projId,
            @Param("sourceType") String sourceType, @Param("apprStatus") String apprStatus, @Param("status") Integer status, 
            @Param("workDairyDate") Date workDairyDate);
    
    @Query("SELECT T FROM MaterialProjDocketEntity T WHERE T.toProjId.projectId=:projId AND T.originStockCodeCategory ='Stockpile' AND T.apprStatus=:apprStatus "
            + "AND (T.docketStatus = null OR T.docketStatus!='C') "
            + "AND T.status=:status AND T.docketDate <= :workDairyDate")
    public List<MaterialProjDocketEntity> findMaterialGeneratedProjDocketsSPMType(@Param("projId") Long projId,
            @Param("apprStatus") String apprStatus, @Param("status") Integer status, 
            @Param("workDairyDate") Date workDairyDate);

    @Query("SELECT T FROM MaterialProjDocketEntity T WHERE T.toProjId.projectId=:projId AND T.status=:status")
    public List<MaterialProjDocketEntity> findMaterialProjDockets(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT distinct T FROM MaterialProjDocketEntity T   WHERE T.fromProjId.projectId in (:projId) "
            + " AND T.docketDate between :fromDate and :toDate order by T.id desc")
    public List<MaterialProjDocketEntity> getMaterialProjDocketsByFilter(@Param("projId") List<Long> projId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
    
    @Query("SELECT distinct T FROM MaterialProjDocketEntity T WHERE T.fromProjId.projectId=:projId order by T.id desc")
    public List<MaterialProjDocketEntity> getMaterialProjDocketsByFilter( @Param("projId") Long projId );

}
