package com.message;

import com.message.exception.MessageException;
import com.message.vo.Message;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class Main {
    private static final String MESSAGE_INBOX_FOLDER = "message_inbox";
    private static final String MESSAGE_ERROR_FOLDER = "message_error";

    public static void main(String[] args) {

        SaleStorage saleStorage = new InMemorySaleStorage();
        MessageParser messageParser = new SimpleMessageParser();
        SimpleMessageProcessor messageProcessor = new SimpleMessageProcessor(saleStorage);
        MessageReader messageReader = new FileMessageReader(MESSAGE_INBOX_FOLDER);

        int messageCount = 0;

        //begin accepting messages
        boolean acceptingMessages = true;

        //loop listening to new messages
        while (true) {
            if (acceptingMessages) {
                String messageRaw = messageReader.readNextMessage();

                //message file successfully read
                if (messageRaw != null) {
                    try {
                        Message message = messageParser.parse(messageRaw);

                        //increase message counter for successfully parsed message
                        messageCount++;

                        messageProcessor.process(message);

                        //print sales information at every 10th message received
                        if (messageCount % 10 == 0) {
                            System.out.println(messageCount + " messages received. Sales made so far: ");
                            printSales(saleStorage);
                        }

                        //print adjustment information when 50 messages are received and stop accepting new messages
                        if (messageCount == 50) {
                            System.out.println(messageCount + " messages received. Process paused. No more messages will be accepted");
                            printAdjustments(saleStorage);

                            //stop reading messages
                            acceptingMessages = false;
                        }
                    } catch (MessageException e) {
                        saveMessageWithError(messageRaw, e.getMessage());
                    }
                }
            }
        }
    }

    private static void saveMessageWithError(String messageRaw, String error) {
        System.out.println("Failure processing message: " + messageRaw);
        System.out.println("  error: " + error);
        File errorFile = new File(MESSAGE_ERROR_FOLDER + "/" + messageRaw.hashCode());
        try (PrintStream fileStream = new PrintStream(errorFile)) {
            fileStream.println(new SimpleDateFormat().format(new Date()));
            fileStream.println(messageRaw);
            fileStream.println(error);
        } catch (IOException e) {
            System.out.println("Couldn't write message with error to " + errorFile.getPath());
        }
    }

    private static void printSales(SaleStorage saleStorage) {
        Set<String> soldProducts = saleStorage.getProductsSold();
        for (String soldProduct : soldProducts) {
            Double totalValue = saleStorage.getTotalValue(soldProduct);
            Integer totalSales = saleStorage.getTotalSales(soldProduct);
            System.out.println(String.format("Product '%s': number of sales: %d - total value of sales: $%.2f",
                    soldProduct, totalSales, totalValue));
            System.out.println();
        }
    }

    private static void printAdjustments(SaleStorage saleStorage) {
        Set<String> soldProducts = saleStorage.getProductsSold();
        for (String soldProduct : soldProducts) {
            List<Message> adjustmentsByProduct = saleStorage.getAdjustmentsByProduct(soldProduct);
            if (adjustmentsByProduct != null && adjustmentsByProduct.size() > 0) {
                System.out.println(String.format("Adjustments made to '%s'", soldProduct));
                for (Message message : adjustmentsByProduct) {
                    System.out.println(String.format("    %s $%.2f", message.getOperation(), message.getAdjustment()));
                }
                System.out.println();
            }
        }
    }
}
