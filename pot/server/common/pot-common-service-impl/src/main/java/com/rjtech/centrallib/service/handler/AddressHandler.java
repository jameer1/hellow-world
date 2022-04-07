package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.AddressTO;
import com.rjtech.centrallib.model.CmpAddressEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.req.AddressSaveReq;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class AddressHandler {
    public static List<CmpAddressEntity> convertPOJOToEntity(AddressSaveReq addressSaveReq) {
        List<CmpAddressEntity> cmpAddressEntitys = new ArrayList<CmpAddressEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        CmpAddressEntity cmpAddressEntity = null;
        CompanyMstrEntity companyMstrEntity = null;
        for (AddressTO addressTO : addressSaveReq.getAddressTOs()) {
            cmpAddressEntity = new CmpAddressEntity();
            if (CommonUtil.isNonBlankLong(addressTO.getAddressId())) {
                cmpAddressEntity.setId(addressTO.getAddressId());
            }
            companyMstrEntity = new CompanyMstrEntity();

            companyMstrEntity.setId(addressTO.getCompanyId());
            cmpAddressEntity.setCompanyMstrEntity(companyMstrEntity);
            cmpAddressEntity.setDefaultAddress(addressTO.isDefaultAddress());
            cmpAddressEntity.setAddress(addressTO.getAddress());
            cmpAddressEntity.setCity(addressTO.getCity());
            cmpAddressEntity.setState(addressTO.getState());
            cmpAddressEntity.setPincode(addressTO.getPincode());

            cmpAddressEntity.setStatus(addressTO.getStatus());

            cmpAddressEntitys.add(cmpAddressEntity);
        }
        return cmpAddressEntitys;
    }

}
