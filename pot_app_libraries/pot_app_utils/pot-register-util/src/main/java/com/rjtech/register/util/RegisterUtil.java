package com.rjtech.register.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RegisterUtil {

    public final static String PO_QUANTY = "qty";

    public final static String PROJECT_PO_STATUS_NEW = "N";
    public final static String PROJECT_PO_STATUS_PARTIALLY_DELIVERY = "P";
    public final static String PROJECT_PO_STATUS_COMPLETED = "C";

    public static void main(String[] args) {
        BigDecimal val = new BigDecimal(0);

        for (int i = -1; i < 4; i++) {
            val = val.add(new BigDecimal(.25));
        }

        System.out.println(val.compareTo(new BigDecimal(1)));

        System.out.println(val.setScale(4, RoundingMode.UP));

    }
}
