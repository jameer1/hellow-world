package com.rjtech.rjs.persistence.providers;

public final class TransactionTypeProvider {
    private static final ThreadLocal<TransactionTypes> TRANSACTION_TYPE_HOLDER = new ThreadLocal<TransactionTypes>();

    private TransactionTypeProvider() {
        // TODO Auto-generated constructor stub
    }

    public static void setTransactionType(final TransactionTypes transactionType) {
        TRANSACTION_TYPE_HOLDER.set(transactionType);
    }

    public static TransactionTypes getTransactionType() {
        return (TransactionTypes) TRANSACTION_TYPE_HOLDER.get();
    }

    public static void clearTransactionType() {
        TRANSACTION_TYPE_HOLDER.remove();
    }
}
