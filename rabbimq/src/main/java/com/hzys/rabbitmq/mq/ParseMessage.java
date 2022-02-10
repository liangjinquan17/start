package com.hzys.rabbitmq.mq;

import lombok.Data;

@Data
public class ParseMessage {
    public ParseMessage(String messageId, String messageBody) {
        this.messageId = messageId;
        this.messageBody = messageBody;
    }

    private String messageId;
    private String messageBody;
}
