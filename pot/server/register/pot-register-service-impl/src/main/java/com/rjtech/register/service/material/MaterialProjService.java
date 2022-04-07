package com.rjtech.register.service.material;

import org.springframework.web.multipart.MultipartFile;

import com.rjtech.register.material.req.MaterialFilterReq;
import com.rjtech.register.material.req.MaterialGetReq;
import com.rjtech.register.material.req.MaterialProjSaveReq;
import com.rjtech.register.material.req.MaterialSchItemsReq;
import com.rjtech.register.material.resp.MaterialDeliveryDocketResp;
import com.rjtech.register.material.resp.MaterialProjResp;
import com.rjtech.register.material.resp.MaterialSchItemsResp;

public interface MaterialProjService {

    MaterialProjResp getProjMaterialSchItems(MaterialGetReq materialGetReq);

    MaterialDeliveryDocketResp getMaterialDeliveryDockets(MaterialGetReq materialGetReq);

    MaterialProjResp getMaterialSchItemsByPurchaseOrder(MaterialGetReq materialGetReq);

    MaterialProjResp getProjMaterialSchDetails(MaterialFilterReq materialGetReq);

    MaterialSchItemsResp getMaterialSchItemsByProjectAndLoc(MaterialSchItemsReq req);

    void saveProjMaterialSchItems(MaterialProjSaveReq materialProjectSaveReq);

    void saveProjMaterialSchDocketDetails(MultipartFile[] files, MaterialProjSaveReq materialProjectSaveReq);

}
