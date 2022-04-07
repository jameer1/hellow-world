package com.rjtech.user.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.admin.repository.AdminRepository;
import com.rjtech.user.model.ClientRegMstrEntity;

@Repository
public interface ClientRepository extends AdminRepository<ClientRegMstrEntity, Long> {
    @Query("SELECT c FROM ClientRegMstrEntity c where c.status=1 order by c.code")
    List<ClientRegMstrEntity> findClients();

    @Modifying
    @Query("UPDATE ClientRegMstrEntity c SET c.status=:status  where c.clientId=:clientId")
    void deactivateClient(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Modifying
    @Query("UPDATE UserEntity usr  SET usr.status=:status  where usr.clientRegMstrEntity.clientId=:clientId")
    void deactivateUsersByClientId(@Param("clientId") Long clientId, @Param("status") Integer status);

    @Query("SELECT c FROM ClientRegMstrEntity c order by c.code")
    List<ClientRegMstrEntity> findAllClients();

    @Query("SELECT count(*) FROM ClientRegMstrEntity c where c.email=:email")
    Long findClientByEmail(@Param("email") String email);

    @Query("SELECT c FROM ClientRegMstrEntity c where c.status=1 and c.licence=:licence")
    List<ClientRegMstrEntity> findLienceExpiredMsgAllClient(@Param("licence") Date licence);
}