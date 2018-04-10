package com.message;

import com.message.exception.MessageException;
import com.message.vo.Message;

/**
 * Parses a message creating a Message object of the appropriate type.
 */
public interface MessageParser {

    Message parse(String messageRaw) throws MessageException;
}
