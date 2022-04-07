package com.rjtech.centrallib.service.handler;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.centrallib.dto.ContactsTO;
import com.rjtech.centrallib.model.CmpContactsEntity;
import com.rjtech.centrallib.model.CompanyMstrEntity;
import com.rjtech.centrallib.req.ContactsSaveReq;
import com.rjtech.common.utils.CommonUtil;
import com.rjtech.constants.ApplicationConstants;

public class ContactsHandler {

    public static List<CmpContactsEntity> convertPOJOToEntity(ContactsSaveReq contactsSaveReq) {
        List<CmpContactsEntity> cmpContactsEntitys = new ArrayList<CmpContactsEntity>(
                ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        CmpContactsEntity CmpContactsEntity = null;
        CompanyMstrEntity companyMstrEntity = null;
        for (ContactsTO contactsTO : contactsSaveReq.getContactsTOs()) {
            CmpContactsEntity = new CmpContactsEntity();
            companyMstrEntity = new CompanyMstrEntity();
            if (CommonUtil.isNonBlankLong(contactsTO.getContactId())) {
                CmpContactsEntity.setId(contactsTO.getContactId());
            }

            companyMstrEntity.setId(contactsTO.getCompanyId());
            CmpContactsEntity.setCompanyMstrEntity(companyMstrEntity);

            CmpContactsEntity.setMobileNo(contactsTO.getMobile());
            CmpContactsEntity.setEmail(contactsTO.getEmail());
            CmpContactsEntity.setName(contactsTO.getContactName());
            CmpContactsEntity.setPhoneNo(contactsTO.getPhone());
            CmpContactsEntity.setFax(contactsTO.getFax());
            CmpContactsEntity.setDesig(contactsTO.getDesignation());
            CmpContactsEntity.setDefaultContact(contactsTO.isDefaultContact());
            CmpContactsEntity.setStatus(contactsTO.getStatus());

            cmpContactsEntitys.add(CmpContactsEntity);
        }
        return cmpContactsEntitys;
    }
}
