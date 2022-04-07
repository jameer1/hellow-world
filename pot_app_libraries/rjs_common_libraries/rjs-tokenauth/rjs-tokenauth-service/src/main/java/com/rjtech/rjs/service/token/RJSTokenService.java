package com.rjtech.rjs.service.token;

import com.rjtech.rjs.appuser.utils.AppUserDetails;

public interface RJSTokenService {

    void add(AppUserDetails input, String token);

    void delete(String token);

    String findTokenByUserId(Long userId);

    AppUserDetails fetchUserByToken(String token);

    void updateUserPermission(AppUserDetails appUserDetails);

}
