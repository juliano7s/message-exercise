package com.message.vo;

import java.util.Arrays;
import java.util.List;

public enum MessageType {
    SINGLE(1, 3),     //type, product, value
    MULTI(2, 4),      //type, product, value, number of sales
    ADJUSTMENT(3, 5); //type, product, value, operation, adjustment

    private int code;
    private int numberOfArguments;

    MessageType(int code, int numberOfArguments) {
        this.code = code;
        this.numberOfArguments = numberOfArguments;
    }

    public static MessageType get(int code) {
        List<MessageType> messageTypes = Arrays.asList(MessageType.values());
        return messageTypes.stream()
                .filter(t -> t.getCode() == code).findAny().orElseThrow(IllegalArgumentException::new);
    }

    public int getCode() {
        return code;
    }

    public int getNumberOfArguments() {
        return numberOfArguments;
    }
}


