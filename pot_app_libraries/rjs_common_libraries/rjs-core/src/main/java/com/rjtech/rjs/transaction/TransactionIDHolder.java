package com.rjtech.rjs.transaction;

import java.util.ArrayList;

public final class TransactionIDHolder {

    private TransactionIDHolder() {
    }

    private static final ThreadLocal<ArrayList<Long>> TRANSACTIONID_HOLDER = new ThreadLocal<ArrayList<Long>>();
    private static final ThreadLocal<Long> PARENT_TRANSACTIONID = new ThreadLocal<Long>();

    public static void setTransactionId(Long transactionID) {
        if (transactionID == null) {
            return;
        }
        if (TRANSACTIONID_HOLDER.get() == null) {
            TRANSACTIONID_HOLDER.set(new ArrayList<Long>());
            PARENT_TRANSACTIONID.set(transactionID);

        }
        TRANSACTIONID_HOLDER.get().add(transactionID);
    }

    public static Long getTransactionId() {
        if (TRANSACTIONID_HOLDER.get() == null || TRANSACTIONID_HOLDER.get().isEmpty()) {
            return null;
        }
        return TRANSACTIONID_HOLDER.get().get(TRANSACTIONID_HOLDER.get().size() - 1);
    }

    public static void clearTransactionId() {
        if (TRANSACTIONID_HOLDER.get() != null) {
            TRANSACTIONID_HOLDER.get().remove(TRANSACTIONID_HOLDER.get().size() - 1);
        }

    }

    public static void clearAll() {
        if (TRANSACTIONID_HOLDER.get() != null) {
            TRANSACTIONID_HOLDER.get().clear();
        }
        PARENT_TRANSACTIONID.remove();
        TRANSACTIONID_HOLDER.set(null);
    }

    public static Long getParentTransactionId() {
        return PARENT_TRANSACTIONID.get();
    }

    public static boolean isParent() {
        return PARENT_TRANSACTIONID.get() != null && PARENT_TRANSACTIONID.get().equals(getTransactionId());
    }

}
