package com.rjtech.mw.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.mw.service.user.MWUserMapService;
import com.rjtech.user.req.ClientReq;
import com.rjtech.user.resp.ClientMapResp;
import com.rjtech.user.resp.UserMapResp;

@RestController
@RequestMapping(AdminURLConstants.USER_PARH_URL)
public class MWUserMapController {

    @Autowired
    private MWUserMapService mwUserMapService;

    @RequestMapping(value = AdminURLConstants.GET_USERSERVICE_MAP, method = RequestMethod.POST)
    public ResponseEntity<UserMapResp> UserServiceMap(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<UserMapResp>(mwUserMapService.UserServiceMap(clientReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_CLIENTSERVICE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ClientMapResp> ClientServiceMap(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<ClientMapResp>(mwUserMapService.ClientServiceMap(clientReq), HttpStatus.OK);
    }

    @RequestMapping(value = AdminURLConstants.GET_EMAILSERVICE_MAP, method = RequestMethod.POST)
    public ResponseEntity<ClientMapResp> getEmailServiceMap(@RequestBody ClientReq clientReq) {
        return new ResponseEntity<ClientMapResp>(mwUserMapService.emailServiceMap(clientReq), HttpStatus.OK);
    }

}
