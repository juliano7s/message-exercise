package com.message;

import com.message.exception.MessageException;
import com.message.vo.Message;
import com.message.vo.MessageType;
import com.message.vo.SaleOperation;

/**
 * Parses a message creating a Message object of the appropriate type.
 * Messages can be one of the three MessageType enumerations:
 *  - SINGLE: 1 [product] [value] e.g: 1 apple 5.5
 *  - MULTI: 2 [product] [value] [number_of_sales] e.g 2 apple 5.5 10
 *  - ADJUSTMENT: 3 [product] [value] [operation] [adjustment_value] e.g 3 apple 4.5 add 1
 */
public class SimpleMessageParser implements MessageParser {

    private static int PRODUCT_LABEL_POSITION = 1;
    private static int PRODUCT_VALUE_POSITION = 2;
    private static int NUMBER_OF_SALES_POSITION = 3;
    private static int ADJUSTMENT_OPERATION_POSITION = 3;
    private static int ADJUSTMENT_POSITION = 4;

    public SimpleMessageParser() {

    }

    public Message parse(String messageRaw) throws MessageException {
        validateEmptyMessage(messageRaw);

        String[] messageArguments = messageRaw.split(" ");
        MessageType type = validateMessageType(messageArguments);
        validateArgumentNumber(messageArguments, type);

        Message message = createMessageForType(messageArguments, type);

        return message;
    }

    private Message createMessageForType(String[] messageArguments, MessageType type) throws MessageException {
        Message message = null;

        try {
            String product = messageArguments[PRODUCT_LABEL_POSITION];
            Double value = Double.parseDouble(messageArguments[PRODUCT_VALUE_POSITION]);
            if (value <= 0) {
                throw new IllegalArgumentException("Product value must be positive");
            }

            switch (type) {
                case SINGLE:
                    message = new Message(product, value);
                    break;
                case MULTI:
                    Integer numberOfSales = Integer.parseInt(messageArguments[NUMBER_OF_SALES_POSITION]);
                    if (numberOfSales <= 0) {
                        throw new IllegalArgumentException("Number of sales must be positive");
                    }
                    message = new Message(product, value, numberOfSales);
                    break;
                case ADJUSTMENT:
                    String operation = messageArguments[ADJUSTMENT_OPERATION_POSITION];
                    if (!SaleOperation.exists(operation)) {
                        throw new IllegalArgumentException("Invalid adjustment operation");
                    }
                    Double adjustment = Double.parseDouble(messageArguments[ADJUSTMENT_POSITION]);
                    if (adjustment <= 0D) {
                        throw new IllegalArgumentException("Adjustment value must be positive");
                    }
                    message = new Message(product, value, operation, adjustment);
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new MessageException(e.getMessage(), e);
        }
        return message;
    }

    private static void validateEmptyMessage(String messageRaw) {
        if (messageRaw == null || messageRaw.trim().equals("")) {
            throw new IllegalArgumentException("Message can't be null or empty");
        }
    }

    private static MessageType validateMessageType(String[] messageArguments) throws MessageException {
        MessageType type;
        try {
            type = MessageType.get(Integer.parseInt(messageArguments[0]));
        } catch (IllegalArgumentException e) {
            throw new MessageException("Invalid message type");
        }
        return type;
    }

    private static void validateArgumentNumber(String[] messageArguments, MessageType type) throws MessageException {
        if (messageArguments.length != type.getNumberOfArguments()) {
            throw new MessageException(
                    "Invalid number of arguments for message type " + type.getCode());
        }
    }
}
