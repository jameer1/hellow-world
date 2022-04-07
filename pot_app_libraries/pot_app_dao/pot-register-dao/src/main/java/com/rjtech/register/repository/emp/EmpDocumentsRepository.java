package com.rjtech.register.repository.emp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.register.emp.model.EmpDocumentsEntity;
import com.rjtech.register.repository.RegisterBaseRepository;

public interface EmpDocumentsRepository extends RegisterBaseRepository<EmpDocumentsEntity, Long> {
    @Query("SELECT EDOCS FROM EmpDocumentsEntity EDOCS WHERE EDOCS.empRegisterDtlEntity.id=:empRegId AND EDOCS.status=:status AND EDOCS.empProjRigisterEntity.id=:empProjId AND EDOCS.documentCategory=:category")
    List<EmpDocumentsEntity> findEmpDocuments( @Param("empRegId") Long empId, @Param("empProjId") Long empProjId, @Param("status") Integer status, @Param("category") String category );
}
