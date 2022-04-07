package com.rjtech.centrallib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.centrallib.model.CmpBankAccountEntity;

public interface BankAccountRepository extends CentralLibRepository<CmpBankAccountEntity, Long> {

	@Query("SELECT CBM FROM CmpBankAccountEntity CBM  WHERE CBM.companyMstrEntity.id=:cmpId AND  CBM.status=:status")
	List<CmpBankAccountEntity> findByClientId(@Param("cmpId") Long cmpId, @Param("status") Integer status);
	
	 @Modifying
	 @Query("UPDATE CmpBankAccountEntity CBM SET CBM.status=:status WHERE CBM.bankAccountId  in :bankIds")
	 void deactivateBank(@Param("bankIds") List<Long> bankIds, @Param("status") Integer status);

}
