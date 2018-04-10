package com.message;

import com.message.vo.Message;
import com.message.vo.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleMessageProcessorTest {

    private InMemorySaleStorage saleStorage;

    @Before
    public void init() {
        saleStorage = new InMemorySaleStorage();
    }

    @Test
    public void testProcessSingleMessage() {
        SimpleMessageProcessor messageProcessor = new SimpleMessageProcessor(saleStorage);
        Message message = new Message("apple", 5D);
        messageProcessor.process(message);
        Assert.assertEquals(new Double(5D), saleStorage.getTotalValue("apple"));
        Assert.assertEquals(new Integer(1), saleStorage.getTotalSales("apple"));
    }

    @Test
    public void testProcessMultiMessage() {
        Message multiMessage = new Message("apple", 5D, 5);
        SimpleMessageProcessor messageProcessor = new SimpleMessageProcessor(saleStorage);
        messageProcessor.process(multiMessage);
        Assert.assertEquals(new Double(5 * 5D), saleStorage.getTotalValue("apple"));
        Assert.assertEquals(new Integer(5), saleStorage.getTotalSales("apple"));
    }

    @Test
    public void testProcessAdjustmentMessageAddOperation() {
        SimpleMessageProcessor messageProcessor = new SimpleMessageProcessor(saleStorage);
        Message message = new Message("apple", 5D);
        messageProcessor.process(message);
        Message adjustmentMessage = new Message(
                "apple", 5D, "add", 5D);
        messageProcessor.process(adjustmentMessage);
        Double expectedTotalValue = 20D;
        Assert.assertEquals(expectedTotalValue, saleStorage.getTotalValue("apple"));
        Assert.assertEquals(new Integer(2), saleStorage.getTotalSales("apple"));
    }

    @Test
    public void testProcessAdjustmentMessageSubtractOperation() {
        SimpleMessageProcessor messageProcessor = new SimpleMessageProcessor(saleStorage);
        Message message = new Message("apple", 5D);
        messageProcessor.process(message);
        Message adjustmentMessage = new Message(
                "apple", 5D, "subtract", 1D);
        messageProcessor.process(adjustmentMessage);
        Double expectedTotalValue = 2 * (5D - 1D);
        Assert.assertEquals(expectedTotalValue, saleStorage.getTotalValue("apple"));
    }

    @Test
    public void testProcessAdjustmentMessageSubtractOperationToZero() {
        SimpleMessageProcessor messageProcessor = new SimpleMessageProcessor(saleStorage);
        Message message = new Message("apple", 5D);
        messageProcessor.process(message);
        Message adjustmentMessage = new Message(
                "apple", 0D, "subtract", 5D);
        messageProcessor.process(adjustmentMessage);
        Assert.assertEquals(new Double(0D), saleStorage.getTotalValue("apple"));
        messageProcessor.process(adjustmentMessage);
        Assert.assertEquals(new Double(0D), saleStorage.getTotalValue("apple"));
    }

    @Test
    public void testAdjustmentIsMadeForAllSalesEvenly() {
        SimpleMessageProcessor messageProcessor = new SimpleMessageProcessor(saleStorage);
        Message message = new Message("apple", 5D, 5);
        messageProcessor.process(message);
        Message adjustmentMessage = new Message(
                "apple", 5D, "add", 5D);
        messageProcessor.process(adjustmentMessage);
        for (Sale sale : saleStorage.getSalesByProduct("apple")) {
            Assert.assertEquals(new Double(10D), sale.getValue());
        }
    }
}