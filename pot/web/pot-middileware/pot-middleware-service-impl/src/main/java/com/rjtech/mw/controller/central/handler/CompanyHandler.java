package com.rjtech.mw.controller.central.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.centrallib.dto.CompanyTO;
import com.rjtech.common.constants.CommonConstants;
import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.utils.CommonUtil;

public class CompanyHandler {

    public static Map<Long, LabelKeyTO> getCompanyLabelKeyTO(List<CompanyTO> companyList) {
        Map<Long, LabelKeyTO> companyLabelKeyMap = new HashMap<Long, LabelKeyTO>();
        LabelKeyTO companyLabelKeyTO = null;

        if (CommonUtil.isListHasData(companyList)) {
            for (CompanyTO companyTO : companyList) {
                companyLabelKeyTO = new LabelKeyTO();
                companyLabelKeyTO.setId(companyTO.getId());
                companyLabelKeyTO.setCode(companyTO.getCmpCode());
                companyLabelKeyTO.setName(companyTO.getCmpName());
                companyLabelKeyTO.getDisplayNamesMap().put(CommonConstants.COMPANY_CATG_NAME,
                        companyTO.getCompanyCatagory());
                companyLabelKeyMap.put(companyTO.getId(), companyLabelKeyTO);
            }
        }

        return companyLabelKeyMap;
    }
}
