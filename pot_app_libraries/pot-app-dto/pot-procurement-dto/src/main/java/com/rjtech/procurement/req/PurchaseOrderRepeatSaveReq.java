package com.rjtech.procurement.req;

import com.rjtech.common.dto.ProjectTO;
import com.rjtech.procurement.dto.PreContractTO;

import java.lang.reflect.Field;

public class PurchaseOrderRepeatSaveReq extends ProjectTO {

    /**
     * 
     */
    private static final long serialVersionUID = 6526217036270683215L;

    private PreContractTO preContractTO = new PreContractTO();
    private Long preContractCmpId;
    private Long purchaseOrderId;

    public PreContractTO getPreContractTO() {
        return preContractTO;
    }

    public void setPreContractTO(PreContractTO preContractTO) {
        this.preContractTO = preContractTO;
    }

    public Long getPreContractCmpId() {
        return preContractCmpId;
    }

    public void setPreContractCmpId(Long preContractCmpId) {
        this.preContractCmpId = preContractCmpId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
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
}
