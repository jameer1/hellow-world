package com.rjtech.rjs.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rjtech.rjs.model.token.TokenCacheEntity;

@Repository
public interface TokenCacheRepository extends JpaRepository<TokenCacheEntity, Long> {

    @Query("SELECT tce from TokenCacheEntity tce WHERE tce.token =:token and tce.isActive is true")
    TokenCacheEntity getTokenCacheByToken(@Param("token") String token);

    @Query("SELECT tce from TokenCacheEntity tce " + " WHERE tce.userEntity.userId =:userId and tce.isActive is true")
    TokenCacheEntity getTokenCacheByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("DELETE from TokenCacheEntity tce WHERE tce.token =:token and tce.isActive is true")
    void deleteToken(@Param("token") String token);

}
