package com.message.vo;

public enum SaleOperation {
    ADD,
    SUBTRACT,
    MULTIPLY;

    public static boolean exists(String operation) {
        try {
            SaleOperation.valueOf(operation.toUpperCase());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
