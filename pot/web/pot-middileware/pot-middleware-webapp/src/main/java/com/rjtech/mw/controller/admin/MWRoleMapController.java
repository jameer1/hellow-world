package com.rjtech.mw.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.admin.constans.AdminURLConstants;
import com.rjtech.mw.service.role.MWRoleMapService;
import com.rjtech.role.req.RoleGetReq;
import com.rjtech.role.resp.RoleMapResp;

@RestController
@RequestMapping(AdminURLConstants.ROLE_PARH_URL)
public class MWRoleMapController {

    @Autowired
    private MWRoleMapService mwRoleMapService;

    @RequestMapping(value = AdminURLConstants.GET_ROLESERVICE_MAP, method = RequestMethod.POST)
    public ResponseEntity<RoleMapResp> RoleServiceMap(@RequestBody RoleGetReq roleGetReq) {
        return new ResponseEntity<RoleMapResp>(mwRoleMapService.RoleServiceMap(roleGetReq), HttpStatus.OK);
    }

}
