package com.rjtech.user.service;

import com.rjtech.user.req.ClientReq;
import com.rjtech.user.resp.ClientMapResp;
import com.rjtech.user.resp.UserMapResp;

public interface UserMapService {

    UserMapResp UserServiceMap(ClientReq clientReq);

    ClientMapResp ClientServiceMap(ClientReq clientReq);

    ClientMapResp emailServiceMap(ClientReq clientReq);

}
