package com.rjtech.finance.resp;

import java.util.ArrayList;
import java.util.List;

import com.rjtech.common.dto.TaxableTypeTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.finance.dto.CodeTypesTO;

public class CodeTypesResp extends AppResp {

    private static final long serialVersionUID = 5228532470619377619L;

    private List<CodeTypesTO> codeTypesTOs = null;
    private List<TaxableTypeTO> taxableTypeTOs = null;

    public CodeTypesResp() {
        codeTypesTOs = new ArrayList<CodeTypesTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        taxableTypeTOs = new ArrayList<TaxableTypeTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
    }

    public List<TaxableTypeTO> getTaxableTypeTOs() {
        return taxableTypeTOs;
    }

    public void setTaxableTypeTOs(List<TaxableTypeTO> taxableTypeTOs) {
        this.taxableTypeTOs = taxableTypeTOs;
    }

    public List<CodeTypesTO> getCodeTypesTOs() {
        return codeTypesTOs;
    }

    public void setCodeTypesTOs(List<CodeTypesTO> codeTypesTOs) {
        this.codeTypesTOs = codeTypesTOs;
    }

}
