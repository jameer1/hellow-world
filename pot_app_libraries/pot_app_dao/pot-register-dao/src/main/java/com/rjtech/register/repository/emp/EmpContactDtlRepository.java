package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpContactDtlEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpContactDtlRepository extends RegisterBaseRepository<EmpContactDtlEntity, Long> {

    @Query("SELECT T FROM EmpContactDtlEntity T WHERE T.empRegisterDtlEntity.id=:empRegId AND T.status=:status")
    public EmpContactDtlEntity findEmpContacts(@Param("empRegId") Long empRegId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE EmpContactDtlEntity  T  SET  T.status=:status  where  T.id in :empContactIds ")
    void deactivateEmpContact(@Param("empContactIds") List<Long> empContactIds, @Param("status") Integer status);
}
