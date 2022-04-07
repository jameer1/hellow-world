
package com.rjtech.register.controller.fixedassets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.common.utils.CommonUtil;
import com.rjtech.common.utils.StatusCodes;
import com.rjtech.projectlib.resp.ProjEPSOnLoadResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.fixedassets.req.FixedAssetsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetDeleteReq;
import com.rjtech.register.fixedassets.req.SubAssetsGetReq;
import com.rjtech.register.fixedassets.req.SubAssetsSaveReq;
import com.rjtech.register.fixedassets.resp.FixedAssetsRegisterOnLoadResp;
import com.rjtech.register.fixedassets.resp.SubAssetsResp;
import com.rjtech.register.service.fixedassets.SubAssetsService;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class SubAssetController {

    @Autowired
    private SubAssetsService subAssetsService;

    @RequestMapping(value = RegisterURLConstants.GET_SUB_ASSETS, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> getSubAssets(@RequestBody SubAssetsGetReq subAssetsGetReq) {
        SubAssetsResp resp = subAssetsService.getSubAssets(subAssetsGetReq);
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SAVE_SUBASSETS, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> saveSubAssets(@RequestBody SubAssetsSaveReq subAssetsSaveReq) {
        subAssetsService.saveSubAssets(subAssetsSaveReq);
        SubAssetsGetReq subAssetsGetReq = new SubAssetsGetReq();
        subAssetsGetReq.setFixedAssetid(subAssetsSaveReq.getFixedAssetDtlTO().getFixedAssetid());
        subAssetsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        SubAssetsResp resp = subAssetsService.getSubAssets(subAssetsGetReq);
        resp.cloneAppResp(CommonUtil.getSaveAppResp());
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SUB_ASSETS_DEACTIVATES, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> deactivateSubAsset(@RequestBody SubAssetDeleteReq subAssetDeleteReq) {
        subAssetsService.deactivateSubAsset(subAssetDeleteReq);

        SubAssetsGetReq subAssetsGetReq = new SubAssetsGetReq();
        subAssetsGetReq.setFixedAssetid(subAssetDeleteReq.getFixedAssetid());
        subAssetsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        SubAssetsResp subAssetsResp = subAssetsService.getSubAssets(subAssetsGetReq);

        subAssetsResp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<SubAssetsResp>(subAssetsResp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.SUB_ASSETS_DELETE, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> subAssetDelete(@RequestBody SubAssetDeleteReq subAssetDeleteReq) {
        subAssetsService.subAssetDelete(subAssetDeleteReq);

        SubAssetsGetReq subAssetsGetReq = new SubAssetsGetReq();
        subAssetsGetReq.setFixedAssetid(subAssetDeleteReq.getFixedAssetid());
        subAssetsGetReq.setStatus(StatusCodes.ACTIVE.getValue());

        SubAssetsResp resp = subAssetsService.getSubAssets(subAssetsGetReq);

        resp.cloneAppResp(CommonUtil.getDeactiveAppResp());
        return new ResponseEntity<SubAssetsResp>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_ASSET_BY_ID, method = RequestMethod.POST)
    public ResponseEntity<SubAssetsResp> getAssetById(@RequestBody SubAssetsGetReq subAssetsGetReq) {

        SubAssetsResp subAssetsResp = new SubAssetsResp();
        subAssetsResp.getSubAssetDtlTOs().addAll(subAssetsService.getAssetById(subAssetsGetReq).getSubAssetDtlTOs());

        return new ResponseEntity<SubAssetsResp>(subAssetsResp, HttpStatus.OK);
    }

}
