package com.rjtech.rjs.service.impl.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.repository.CommonRepository;
import com.rjtech.rjs.appuser.utils.AppUserDetails;
import com.rjtech.rjs.model.token.TokenCacheEntity;
import com.rjtech.rjs.repository.token.TokenCacheRepository;
import com.rjtech.rjs.service.token.RJSTokenService;
import com.rjtech.rjs.service.xml.XMLUtilService;

@Service(value = "rjsTokenService")
@Transactional
public class RJSTokenServiceImpl implements RJSTokenService {

    @Autowired
    private XMLUtilService xmlUtilService;

    @Autowired
    private TokenCacheRepository tokenCacheRepository;

    @Autowired
    private CommonRepository userRepository;

    public void add(AppUserDetails input, String token) {
        persistTokenInDatabase(token, input);
    }

    public void delete(String key) {
        deleteTokenFromDataBase(key);
    }

    public String findTokenByUserId(Long userId) {
        String token = null;
        TokenCacheEntity tokenCacheEntity = tokenCacheRepository.getTokenCacheByUserId(userId);
        if (tokenCacheEntity != null) {
            token = tokenCacheEntity.getToken();
        }
        return token;
    }

    private void persistTokenInDatabase(String token, AppUserDetails input) {
        TokenCacheEntity tokenCacheEntity = tokenCacheRepository.getTokenCacheByUserId(input.getUserId());
        if (tokenCacheEntity == null) {
            tokenCacheEntity = new TokenCacheEntity();
        }
        tokenCacheEntity.setToken(token);
        tokenCacheEntity.setAppUserDetails(xmlUtilService.getXMLString(input));
        UserMstrEntity userEntity = userRepository.findOne(input.getUserId());
        tokenCacheEntity.setClientEntity(userEntity.getClientRegEntity());
        tokenCacheEntity.setUserEntity(userEntity);
        tokenCacheEntity.setActive(true);

        tokenCacheRepository.save(tokenCacheEntity);
    }

    public AppUserDetails fetchUserByToken(final String token) {
        TokenCacheEntity tokenCacheEntity = tokenCacheRepository.getTokenCacheByToken(token);
        AppUserDetails appUserDetails = null;
        if (tokenCacheEntity != null) {
            String appUserDetailsstring = tokenCacheEntity.getAppUserDetails();
            appUserDetails = unMarshalUserDetails(appUserDetailsstring);
        }
        return appUserDetails;
    }

    private AppUserDetails unMarshalUserDetails(String appUserDetailsstring) {
        Object object = xmlUtilService.getObjectFromXMLString(appUserDetailsstring);
        AppUserDetails appUserDetails = null;
        if (object instanceof AppUserDetails) {
            appUserDetails = (AppUserDetails) object;
        }
        return appUserDetails;
    }

    private void deleteTokenFromDataBase(final String token) {
        tokenCacheRepository.deleteToken(token);
    }

    public void updateUserPermission(AppUserDetails appUserDetails) {
        TokenCacheEntity tokenCacheEntity = tokenCacheRepository.getTokenCacheByToken(appUserDetails.getToken());
        tokenCacheEntity.setAppUserDetails(xmlUtilService.getXMLString(appUserDetails));
        tokenCacheRepository.save(tokenCacheEntity);
    }
}