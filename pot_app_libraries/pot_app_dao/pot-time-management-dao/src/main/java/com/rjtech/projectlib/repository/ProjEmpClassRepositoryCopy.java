package com.rjtech.projectlib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.timemanagement.attendance.model.ProjEmpClassMstrEntityCopy;

public interface ProjEmpClassRepositoryCopy extends JpaRepository<ProjEmpClassMstrEntityCopy, Long> {

    @Query("SELECT PEC FROM ProjEmpClassMstrEntityCopy PEC  WHERE PEC.projectId.projectId = :projId "
            + " and PEC.empClassMstrEntity.id = :empId AND  PEC.status=:status ")
    ProjEmpClassMstrEntityCopy getUserProjEmpClasses(@Param("projId") Long projId, @Param("empId") Long empId,
            @Param("status") Integer status);

    @Query("SELECT DISTINCT PEC.empClassMstrEntity.id FROM ProjEmpClassMstrEntityCopy PEC  WHERE PEC.projectId.projectId IN :projIds "
            + " and PEC.projEmpCategory = :empCategory AND  PEC.status=1")
    List<Long> getEmpClassByEmpCategoryName(@Param("projIds") List<Long> projIds,
            @Param("empCategory") String empCategory);

    @Query("SELECT DISTINCT PEC.projEmpCategory FROM ProjEmpClassMstrEntityCopy PEC  WHERE PEC.empClassMstrEntity.id = :empClassId "
            + "AND  PEC.status=1 AND PEC.projectId.projectId = :projId ")
    String getEmpCategoryNameByEmpClassId(@Param("empClassId") Long empClassId, @Param("projId") Long projId);

}
