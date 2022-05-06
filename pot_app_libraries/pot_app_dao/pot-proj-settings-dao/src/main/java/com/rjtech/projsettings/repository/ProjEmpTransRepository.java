package com.rjtech.projsettings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.EmpTransNormalTimeEntity;

public interface ProjEmpTransRepository extends ProjSettingsBaseRepository<EmpTransNormalTimeEntity, Long> {

    @Query("SELECT PET FROM EmpTransNormalTimeEntity PET WHERE (( :projId IS NULL AND PET.projId.projectId IS NULL) OR PET.projId.projectId=:projId ) AND PET.status=:status")
    public List<EmpTransNormalTimeEntity> findProjEmpTrans(@Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT PET FROM com.rjtech.projsettings.model.EmpTransNormalTimeEntity PET WHERE PET.isDefault='Y' AND PET.projId IS NULL ")
    public List<EmpTransNormalTimeEntity> findDefaultProjEmpTrans();

    @Query("SELECT PET FROM EmpTransNormalTimeEntity PET where PET.status = 1 and PET.projId.projectId = :projId and "
            + " PET.emptransType = :transType")
    public EmpTransNormalTimeEntity findByProjIdTransType(@Param("projId") Long projId,
            @Param("transType") String transType);
    
    @Query("SELECT PET FROM EmpTransNormalTimeEntity PET WHERE (( :projId IS NULL AND PET.projId.projectId IS NULL) OR PET.projId.projectId=:projId ) "
    		+ "AND PET.status=:status")
    public EmpTransNormalTimeEntity findEmpTransNormalTimeByProjId(@Param("projId") Long projId, @Param("status") Integer status);
//user created Query used to count the deployment id
    @Query("SELECT PET FROM EmpTransNormalTimeEntity PET where PET.status = 1 and PET.projId.projectId = :projId and "
            + " PET.emptransType = :transType")
    public List<EmpTransNormalTimeEntity> findByProjIdTransTypes(@Param("projId") Long projId,
            @Param("transType") String transType);
    
    
}
