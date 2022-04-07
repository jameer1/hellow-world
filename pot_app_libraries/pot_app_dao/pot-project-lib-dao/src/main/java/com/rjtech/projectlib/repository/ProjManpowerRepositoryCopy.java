/*
 * package com.rjtech.projectlib.repository;
 * 
 * import java.util.List;
 * 
 * import org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.data.jpa.repository.Modifying; import
 * org.springframework.stereotype.Repository;
 * 
 * import com.rjtech.projsettings.model.ProjManpowerEntity;
 * 
 * import org.springframework.data.jpa.repository.JpaRepository;
 * 
 * //import com.rjtech.projectlib.model.ProjManpowerEntityCopy;
 * 
 * @Repository public interface ProjManpowerRepositoryCopy extends
 * JpaRepository<ProjManpowerEntity, Long> {
 * 
 * @Modifying
 * 
 * @Query("UPDATE ProjManpowerEntity MPD SET MPD.itemStatus='APPROVED',MPD.status=:status,MPD.requestedFrom='FROM_PROJECT_BUDGET' where MPD.id=:manpowerId"
 * ) public void updateProjManpowerStatusById( @Param("manpowerId") Long
 * manpowerId, @Param("status") Integer status );
 * 
 * }
 */
