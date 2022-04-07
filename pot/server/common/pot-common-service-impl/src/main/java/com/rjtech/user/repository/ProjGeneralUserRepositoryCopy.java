package com.rjtech.user.repository;


import java.util.List;

import com.rjtech.projectlib.repository.EPSProjBaseRepository;
import com.rjtech.role.model.ProjGeneralMstrUserEntityCopy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjGeneralUserRepositoryCopy extends EPSProjBaseRepository<ProjGeneralMstrUserEntityCopy, Long> {

    //@Query("SELECT PGV.projMstrEntity.projectId FROM ProjGeneralMstrUserEntityCopy PGV WHERE PGV.respManager.userId=?1 and PGV.status=?2 and PGV.contractType=?3")
    //List<Long> findUserProjIds(@Param("userId") Long userId, @Param("status") Integer status,@Param("contractType") String contractType);
    @Query("SELECT PGV.projMstrEntity.projectId FROM ProjGeneralMstrUserEntityCopy PGV WHERE PGV.status=?1 and PGV.contractType=?2")
    List<Long> findUserProjIds(@Param("status") Integer status,@Param("contractType") String contractType);
}
