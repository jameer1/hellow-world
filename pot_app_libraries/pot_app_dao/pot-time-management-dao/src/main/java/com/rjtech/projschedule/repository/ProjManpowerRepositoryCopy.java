package com.rjtech.projschedule.repository;

//import com.rjtech.projschedule.model.ProjManpowerEntityCopy;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rjtech.projsettings.model.ProjManpowerEntity;


public interface ProjManpowerRepositoryCopy extends JpaRepository<ProjManpowerEntity, Long> {
	
	
    @Query("SELECT MPD FROM ProjManpowerEntity MPD  WHERE MPD.empClassMstrEntity.id=:projId ")
    public List<ProjManpowerEntity> findbyIds(@Param("projId") Long projId);
    
    @Modifying
    @Query("UPDATE ProjManpowerEntity MPD SET MPD.itemStatus='APPROVED',MPD.status=:status,MPD.requestedFrom='FROM_PROJECT_BUDGET' where MPD.id=:manpowerId")
    public void updateProjManpowerStatusById( @Param("manpowerId") Long manpowerId, @Param("status") Integer status );

}
