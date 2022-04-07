package com.rjtech.finance.req;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.ClientTO;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.CodeTypesTO;

public class CodeTypesSaveReq extends ClientTO {

    private static final long serialVersionUID = -4819561725479979123L;

    private List<CodeTypesTO> codeTypesTOs = new ArrayList<CodeTypesTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    private Long taxId;

    public List<CodeTypesTO> getCodeTypesTOs() {
        return codeTypesTOs;
    }

    public void setCodeTypesTOs(List<CodeTypesTO> codeTypesTOs) {
        this.codeTypesTOs = codeTypesTOs;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

}
