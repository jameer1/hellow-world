package com.rjtech.projsettings.repository;

import org.springframework.data.jpa.repository.Query;

import com.rjtech.projsettings.model.LeaveAddtionalTimeApprEntity;

public interface ProjLeaveApprRepository extends ProjSettingsBaseRepository<LeaveAddtionalTimeApprEntity, Long> {

    @Query("SELECT T  FROM LeaveAddtionalTimeApprEntity T where T.status=1 AND T.latest=true")
    public LeaveAddtionalTimeApprEntity findLatestApproval();

}
