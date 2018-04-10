package com.message;

import com.message.exception.MessageException;
import com.message.vo.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageParserTest {

    private MessageParser messageParser;

    @Before
    public void init() {
        messageParser = new SimpleMessageParser();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseNullMessage() throws MessageException {
        messageParser.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyMessage() throws MessageException {
        messageParser.parse(" ");
    }

    @Test(expected = MessageException.class)
    public void testParseMalformedMessage() throws MessageException {
        String malFormedMessage = "--";
        messageParser.parse(malFormedMessage);
    }

    @Test(expected = MessageException.class)
    public void testParseMalformedMessageInvalidType() throws MessageException {
        String malFormedMessageInvalidMessageType = "4 apple 5.5";
        messageParser.parse(malFormedMessageInvalidMessageType);
    }

    @Test(expected = MessageException.class)
    public void testParseMalformedMessageNumberOfArguments() throws MessageException {
        String malFormedMessageSingleNumberOfArguments = "1 apple 5.5 foo";
        messageParser.parse(malFormedMessageSingleNumberOfArguments);
        String malFormedMessageMultiNumberOfArguments = "2 apple 5.5 15 foo";
        messageParser.parse(malFormedMessageMultiNumberOfArguments);
        String malFormedMessageAdjustmentNumberOfArguments = "3 apple 5.5 add 4 foo";
        messageParser.parse(malFormedMessageAdjustmentNumberOfArguments);
    }

    @Test(expected = MessageException.class)
    public void testParseMalformedMessageInvalidOperationForAdjustmentType() throws MessageException {
        String malFormedMessageTypeAdjustmentInvalidOperation = "3 apple 5.5 divide 4";
        messageParser.parse(malFormedMessageTypeAdjustmentInvalidOperation);
    }

    @Test
    public void testParseMessageTypeSingle() throws MessageException {
        String validMessageTypeSingleRaw = "1 apple 5.5";
        Message messageTypeSingleObject = new Message("apple", 5.5);
        Message parsedMessageTypeSingle = messageParser.parse(validMessageTypeSingleRaw);
        Assert.assertEquals(parsedMessageTypeSingle,messageTypeSingleObject);
    }

    @Test
    public void testParseMessageTypeMulti() throws MessageException {
        String validMessageTypeMultiRaw = "2 apple 5.5 15";
        Message messageTypeMultiObject = new Message("apple", 5.5, 15);
        Message parsedMessageTypeMulti = messageParser.parse(validMessageTypeMultiRaw);
        Assert.assertEquals(parsedMessageTypeMulti, messageTypeMultiObject);
    }

    @Test
    public void testParseMessageTypeAdjustment() throws MessageException {
        String validMessageTypeAdjustmentRaw = "3 apple 5.3 add 4";
        Message messageTypeAdjustmentObject = new Message("apple", 5.3, "add", 4.0);
        Message parsedMessageTypeAdjustment = messageParser.parse(validMessageTypeAdjustmentRaw);
        Assert.assertEquals(parsedMessageTypeAdjustment, messageTypeAdjustmentObject);
    }
}