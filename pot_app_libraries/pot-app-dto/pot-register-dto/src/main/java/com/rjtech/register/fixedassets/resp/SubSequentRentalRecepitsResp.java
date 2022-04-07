package com.rjtech.register.fixedassets.resp;

import java.util.ArrayList;
import java.util.List;
import com.rjtech.common.resp.AppResp;
import com.rjtech.register.fixedassets.dto.SubSequentRentalRecepitsDtlTO;

public class SubSequentRentalRecepitsResp extends AppResp {
    private static final long serialVersionUID = 1L;    
    private List<SubSequentRentalRecepitsDtlTO> subSequentRentalRecepitsDtlTO = new ArrayList<SubSequentRentalRecepitsDtlTO>();    
    public List<SubSequentRentalRecepitsDtlTO> getSubSequentRentalRecepitsDtlTO() {
        return subSequentRentalRecepitsDtlTO;
    }
    public void setSubSequentRentalRecepitsDtlTO(List<SubSequentRentalRecepitsDtlTO> subSequentRentalRecepitsDtlTO) {
        this.subSequentRentalRecepitsDtlTO = subSequentRentalRecepitsDtlTO;
    }
}
