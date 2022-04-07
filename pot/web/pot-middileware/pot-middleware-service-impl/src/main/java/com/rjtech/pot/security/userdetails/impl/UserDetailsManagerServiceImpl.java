package com.rjtech.pot.security.userdetails.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rjtech.common.model.ClientRegEntity;
import com.rjtech.common.model.UserMstrEntity;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.login.repository.LoginRepositoryCopy;
import com.rjtech.rjs.appuser.utils.AppUserDetails;

@Service(value = "userDetailsService")
public class UserDetailsManagerServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginRepositoryCopy loginRepository;

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails = null;

        logger.info(" UserDetailsManagerServiceImpl  loadUserByUsername " + userName);

        UserMstrEntity userEntity = null;

        String[] userInoputs = userName.split("#");

        userEntity = loginRepository.findUserById(Long.valueOf(userInoputs[0]), StatusCodes.ACTIVE.getValue(),
                Integer.valueOf(userInoputs[1]));

        logger.info(" UserDetailsManagerServiceImpl  loadUserByUsername  userEntity " + userEntity);

        if (userEntity == null) {
            throw new UsernameNotFoundException("UserDetails not found. Please contact Administrator");
        }

        AppUserDetails appUserDetails = new AppUserDetails(userEntity.getUserName(), "NA",
                AuthorityUtils.NO_AUTHORITIES);
        appUserDetails.setDisplayName(userEntity.getDisplayName());
        appUserDetails.setName(userEntity.getDisplayName());
        appUserDetails.setUserId(userEntity.getUserId());
        userDetails = appUserDetails;
        return userDetails;
    }

}
