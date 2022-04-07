/*
 * package com.rjtech.procurement.repository;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param;
 * 
 * import com.rjtech.procurement.model.ProcurementAddtionalTimeApprEntity;
 * 
 * public interface ProcurementAddltionalTimeRepository extends
 * JpaRepository<ProcurementAddtionalTimeApprEntity, Long> {
 * 
 * 
 * @Query("SELECT PAT FROM ProcurementAddtionalTimeApprEntity PAT WHERE PAT.preContractEntity.id=:procurementId "
 * +
 * "AND PAT.procureStage=:procureStage AND PAT.notificationStatus='Approved' AND PAT.status=1"
 * ) public ProcurementAddtionalTimeApprEntity
 * findNormalTimeId(@Param("procurementId") Long procurementId,
 * 
 * @Param("procureStage") String procureStage);
 * 
 * }
 */