package com.rjtech.register.controller.material;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rjtech.aws.common.s3.file.service.impl.AwsS3FileServiceImpl;
import com.rjtech.common.resp.LabelKeyTOResp;
import com.rjtech.register.constans.RegisterURLConstants;
import com.rjtech.register.controller.common.RegisterCommonController;
import com.rjtech.register.material.model.MaterialPODeliveryDocketEntity;
import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.resp.MaterialDeliveryDocketResp;
import com.rjtech.register.service.material.MaterialDeliveryDocketSevice;

@RestController
@RequestMapping(RegisterURLConstants.REGISTER_PARH_URL)
public class MaterialDeliveryDocketController extends RegisterCommonController {

    @Autowired
    private MaterialDeliveryDocketSevice materialDeliveryDocketSevice;
    
    @Autowired
    private AwsS3FileServiceImpl awsS3FileService;

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_DELIVERY_DOCKET_DETAILS, method = RequestMethod.POST)
    public ResponseEntity<LabelKeyTOResp> getMaterialDeliveryDocketDetails(@RequestBody MaterialGetReq materialGetReq) {
        return new ResponseEntity<LabelKeyTOResp>(
                materialDeliveryDocketSevice.getMaterialDeliveryDocketDetails(materialGetReq), HttpStatus.OK);
    }

    @RequestMapping(value = RegisterURLConstants.GET_MATERIAL_SCH_ITEM_DELIVERY_DOCKETS, method = RequestMethod.POST)
    public ResponseEntity<MaterialDeliveryDocketResp> getMaterialSchItemDeliveryDockets(
            @RequestBody MaterialFilterReq materialFilterReq) {
        MaterialDeliveryDocketResp materialDeliveryDocketResp = new MaterialDeliveryDocketResp();
        materialDeliveryDocketResp.setLabelKeyTOs(
                materialDeliveryDocketSevice.getMaterialSchItemDeliveryDockets(materialFilterReq).getLabelKeyTOs());
        return new ResponseEntity<MaterialDeliveryDocketResp>(materialDeliveryDocketResp, HttpStatus.OK);
    }
    
    @GetMapping(value = "downloadMaterialFile")
    public ResponseEntity<ByteArrayResource> downloadMaterialFile(@RequestParam("mapId") Long mapId) throws IOException {
        MaterialPODeliveryDocketEntity materialProjDtl = materialDeliveryDocketSevice.getMaterialProjEntity(mapId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(materialProjDtl.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + materialProjDtl.getFileName() + "\"")
                .body(new ByteArrayResource(awsS3FileService.downloadFile(materialProjDtl.getFileKey())));
    }

}
