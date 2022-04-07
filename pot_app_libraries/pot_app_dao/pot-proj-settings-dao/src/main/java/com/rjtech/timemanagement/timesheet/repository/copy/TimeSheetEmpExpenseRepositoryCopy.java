package com.rjtech.timemanagement.timesheet.repository.copy;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.timemanagement.timesheet.model.TimeSheetEmpExpenseEntity;

//import com.rjtech.timesheet.TimeSheetEmpExpenseEntityCopy;

@Repository
public interface TimeSheetEmpExpenseRepositoryCopy extends JpaRepository<TimeSheetEmpExpenseEntity, Long> {

    @Query("SELECT TSDE.projCostItemEntity.id, sum(TSDE.amount) FROM TimeSheetEmpExpenseEntity TSDE "
            + " WHERE TSDE.timeSheetEmpDtlEntity.empRegisterDtlEntity.id in (:empIds) AND "
            + " lower(TSDE.timeSheetEmpDtlEntity.timeSheetEntity.apprStatus)='approved' AND lower(TSDE.apprStatus)='submitted' "
            + " GROUP BY TSDE.projCostItemEntity.id HAVING sum(TSDE.amount) > 0 ")
    List<Object[]> findExpenses(@Param("empIds") Set<Long> empIds);

    @Query("SELECT TSDE.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity, TSDE.timeSheetEmpDtlEntity.empRegisterDtlEntity, TSDE.projCostItemEntity, TSDE.amount, "
            + " PEC.projEmpCategory, TSDE.date FROM TimeSheetEmpExpenseEntity TSDE "
            + " LEFT JOIN ProjEmpClassMstrEntity PEC ON PEC.empClassMstrEntity = TSDE.timeSheetEmpDtlEntity.empRegisterDtlEntity.empClassMstrEntity "
            + " AND PEC.projectId = TSDE.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity "
            + " WHERE lower(TSDE.timeSheetEmpDtlEntity.timeSheetEntity.apprStatus)='approved' AND lower(TSDE.apprStatus)='submitted' AND "
            + " TSDE.timeSheetEmpDtlEntity.timeSheetEntity.projMstrEntity.projectId in (:projIds) AND lower(PEC.projEmpCategory) in (:empCats) AND "
            + " TSDE.timeSheetEmpDtlEntity.empRegisterDtlEntity.companyMstrEntity.id in (:cmpIds) AND TSDE.projCostItemEntity.id in (:costIds) AND "
            + " TSDE.date BETWEEN :fromDate AND :toDate order by TSDE.date ")
    List<Object[]> findExpenses(@Param("projIds") List<Long> projIds, @Param("empCats") List<String> empCats,
            @Param("cmpIds") List<Long> cmpIds, @Param("costIds") List<Long> costIds, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
