package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.eps.model.ProjMstrEntity;

@Repository
public interface EPSProjRepository extends EPSProjBaseRepository<ProjMstrEntity, Long> {
    @Query("SELECT proj FROM com.rjtech.eps.model.ProjMstrEntity proj WHERE proj.status=:status and proj.parentProjectMstrEntity.projectId is null and proj.clientId.clientId=:clientId ORDER BY  proj.code")
    public List<ProjMstrEntity> findEPSProjects(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT proj FROM ProjMstrEntity proj WHERE  proj.clientId.clientId=:clientId and proj.projectId=:projId and proj.status=:status  ORDER BY  proj.code")
    public List<ProjMstrEntity> findEPSProjectsById(@Param("clientId") Long clientId, @Param("projId") Long projId,
            @Param("status") Integer status);

    @Query("SELECT proj FROM ProjMstrEntity proj WHERE  proj.proj=true and proj.projectId=:projId and proj.clientId.clientId=:clientId  and proj.status=:status ORDER BY  proj.code")
    public List<ProjMstrEntity> findProjectsById(@Param("projId") Long projId, @Param("clientId") Long clientId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT proj.parentProjectMstrEntity FROM ProjMstrEntity proj WHERE proj.status=:status and proj.proj=true and proj.clientId.clientId=:clientId ORDER BY  proj.code")
    public List<ProjMstrEntity> findProjectEps(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT proj FROM ProjMstrEntity proj WHERE  proj.clientId.clientId=:clientId  and proj.status=:status and proj.proj=true  ORDER BY  proj.code")
    public List<ProjMstrEntity> findProjects(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE ProjMstrEntity proj  SET proj.status=:status  where proj.projectId in :projectIds or proj.parentProjectMstrEntity.projectId in :projectIds")
    void deactivateProjectEps(@Param("projectIds") List<Long> projectIds, @Param("status") Integer status);

    @Query("SELECT proj FROM ProjMstrEntity proj WHERE proj.status=:status and proj.clientId.clientId=:clientId AND proj.proj=false AND proj.parentProjectMstrEntity is null")
    public List<ProjMstrEntity> findOnlyEPSProjects(@Param("status") Integer status, @Param("clientId") Long clientId);

    @Modifying
    @Query("UPDATE ProjMstrEntity proj SET proj.assignedStatus=:assignedStatus  where proj.projectId in :projIds ")
    void epsAssignedProjectsStatus(@Param("projIds") List<Long> projIds,
            @Param("assignedStatus") boolean assignedStatus);

    @Query("SELECT proj FROM ProjMstrEntity proj ORDER BY  proj.code")
    public List<ProjMstrEntity> findAllProjects();

    @Query("SELECT proj FROM ProjMstrEntity proj where proj.clientId.clientId = :clientId ORDER BY  proj.code")
    public List<ProjMstrEntity> findProjectsByClientId(@Param("clientId") long clientId);

    @Query("SELECT proj FROM ProjMstrEntity proj WHERE  proj.projectId=:projId")
    public List<ProjMstrEntity> findEPSProjectName(Long projId);

    @Query("SELECT COUNT(EPM) FROM ProjMstrEntity EPM where EPM.status = 1 and EPM.proj = 1 and EPM.parentProjectMstrEntity.projectId = :epsId")
    int getProjCountByEpsId(@Param("epsId") long epsId);

}
