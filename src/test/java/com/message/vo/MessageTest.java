package com.message.vo;

import org.junit.Assert;
import org.junit.Test;

public class MessageTest {

    @Test
    public void testEquals() {
        Message message1 = new Message("apple", 2.0);
        Assert.assertNotEquals(message1, null);
        Assert.assertNotEquals(message1, new Object());

        Message equalToMessage1 = clone(message1);
        Assert.assertEquals(message1, equalToMessage1);

        Message notEqualToMessage1 = clone(message1);
        notEqualToMessage1.setNumberOfSales(2);
        Assert.assertNotEquals(message1, notEqualToMessage1);
    }

    private static Message clone(Message message) {
        return new Message(
                message.getType(),
                message.getSale(),
                message.getNumberOfSales(),
                message.getOperation(),
                message.getAdjustment());
    }
}