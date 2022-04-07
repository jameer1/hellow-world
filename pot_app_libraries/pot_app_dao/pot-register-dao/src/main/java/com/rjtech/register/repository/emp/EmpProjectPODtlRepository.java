package com.rjtech.register.repository.emp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.register.emp.model.EmpProjRegisterPODtlEntity;

@Repository
public interface EmpProjectPODtlRepository extends JpaRepository<EmpProjRegisterPODtlEntity, Long> {

    @Query("select T FROM EmpProjRegisterPODtlEntity T where T.projMstrEntity.projectId =:projId"
            + " AND  T.purchaseOrderEntity.id =:poId " + " AND  T.preContractsEmpDtlEntity.id =:schId "
            + " AND  T.preContractsEmpCmpEntity.id =:schCompId ")
    public EmpProjRegisterPODtlEntity getProjectPObyProjIdAndSchIdAndCompanySchId(@Param("projId") Long projId,
            @Param("poId") Long poId, @Param("schId") Long schId, @Param("schCompId") Long schCompId);

}
