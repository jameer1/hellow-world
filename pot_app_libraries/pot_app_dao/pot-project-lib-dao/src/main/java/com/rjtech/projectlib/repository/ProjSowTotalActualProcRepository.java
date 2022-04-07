package com.rjtech.projectlib.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.projectlib.dto.TotalActualTO;
import com.rjtech.projectlib.model.ProjSOWItemEntity;
import com.rjtech.timemanagement.workdairy.model.WorkDairyProgressDtlEntity;

@Repository
public interface ProjSowTotalActualProcRepository extends JpaRepository<WorkDairyProgressDtlEntity, Long> {

    @Query("SELECT WDPD.sowId.id, SUM(WDPD.value) FROM WorkDairyProgressDtlEntity WDPD "
            + "WHERE WDPD.apprStatus = 'Approved'  AND  WDPD.sowId.projMstrEntity.projectId = :projId  GROUP  BY WDPD.sowId")
    List<Object[]> findTotalActualQuantitiesByProjId(@Param("projId") Long projId);

    @Query("SELECT PCSW.sowId.id, SUM(PCSW.quantity) FROM  PrecontractSowDtlEntity PCSW WHERE "
            + "PCSW.preContractEntity.contarctStageStatus = 'Purchase Order' AND "
            + "PCSW.preContractEntity.purchaseOrderStatus = 'PO Issued' AND "
            + "PCSW.sowId.projMstrEntity.projectId = :projId GROUP BY PCSW.sowId")
    List<Object[]> findPrecontractTotalActualQuantitiesByProjId(@Param("projId") Long projId);
    
    @Query("SELECT SUM(WDPD.value) FROM WorkDairyProgressDtlEntity WDPD "
            + "WHERE WDPD.sowId = :sowId AND WDPD.apprStatus = 'Approved'")
    Double getActualQtyOfSow(@Param("sowId") ProjSOWItemEntity sowId);


    default Map<Long, TotalActualTO> findTotalActualQuantities(Long projId) {

        Map<Long, TotalActualTO> map = new HashMap<>();

        List<Object[]> queryResult = findTotalActualQuantitiesByProjId(projId);
        calculateResult(queryResult, map);

        queryResult = findPrecontractTotalActualQuantitiesByProjId(projId);
        calculateResult(queryResult, map);

        return map;
    }

    default void calculateResult(List<Object[]> result, Map<Long, TotalActualTO> map) {
        Long sowid = null;
        Long totalCost = null;
        for (Object[] obj : result) {
            sowid = (Long) obj[0];
            if (null != obj[1]) {
            	try {
            		totalCost = new Double((double) obj[1]).longValue();
            	} catch (ClassCastException e) {
            		totalCost = new Long(obj[1].toString());
            	}
			}
            if (map.containsKey(sowid)) {
                TotalActualTO actualTO = map.get(sowid);
                actualTO.setActualQuantity(actualTO.getActualQuantity() + totalCost);
            } else {
                TotalActualTO toObj = new TotalActualTO();
                toObj.setId(sowid);
                toObj.setActualQuantity(totalCost);
                map.put(sowid, toObj);
            }
        }

    }

}
