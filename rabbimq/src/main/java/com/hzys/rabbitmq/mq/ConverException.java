package com.hzys.rabbitmq.mq;

import org.springframework.amqp.core.Message;

class ConverException extends RuntimeException{

    private Message body;
    public ConverException(Message body, String message, Throwable cause){
        super(message, cause);
        this.body = body;
    }

    public Message getBody(){
        return this.body;
    }
}