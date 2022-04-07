package com.rjtech.procurement.resp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rjtech.common.dto.LabelKeyTO;
import com.rjtech.common.resp.AppResp;
import com.rjtech.constants.ApplicationConstants;
import com.rjtech.procurement.dto.PurchaseOrderTO;

public class PurchaseOrderResp extends AppResp {

    /**
     * 
     */
    private static final long serialVersionUID = 3719597858807086400L;
    private List<PurchaseOrderTO> purchaseOrderTOs = null;
    private Map<Long, LabelKeyTO> usersMap = null;
    private Map<Long, LabelKeyTO> companyMap = null;
    private Map<Long, LabelKeyTO> userProjMap = null;
    private PreContractResp preContractResp = null;

    public PurchaseOrderResp() {
        purchaseOrderTOs = new ArrayList<PurchaseOrderTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);
        usersMap = new HashMap<Long, LabelKeyTO>();
        companyMap = new HashMap<Long, LabelKeyTO>();
        userProjMap = new HashMap<Long, LabelKeyTO>(ApplicationConstants.ARRAYLIST_DEFAULT_SIZE);

    }

    public List<PurchaseOrderTO> getPurchaseOrderTOs() {
        return purchaseOrderTOs;
    }

    public void setUsersMap(Map<Long, LabelKeyTO> usersMap) {
        this.usersMap = usersMap;
    }

    public Map<Long, LabelKeyTO> getUsersMap() {
        return usersMap;
    }

    public Map<Long, LabelKeyTO> getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map<Long, LabelKeyTO> companyMap) {
        this.companyMap = companyMap;
    }

    public Map<Long, LabelKeyTO> getUserProjMap() {
        return userProjMap;
    }

    public void setUserProjMap(Map<Long, LabelKeyTO> userProjMap) {
        this.userProjMap = userProjMap;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

    public PreContractResp getPreContractResp() {
        return preContractResp;
    }

    public void setPreContractResp(PreContractResp preContractResp) {
        this.preContractResp = preContractResp;
    }
}
