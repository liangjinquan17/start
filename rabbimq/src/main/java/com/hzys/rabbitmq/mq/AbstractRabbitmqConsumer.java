package com.hzys.rabbitmq.mq;

import com.alibaba.fastjson.JSONObject;
import com.hzys.rabbitmq.service.IRabbitmqConsumerRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.function.Function;

@Slf4j
public abstract class AbstractRabbitmqConsumer<R> implements Function<Message, R> {

    private final String MESSAGE_ID_KEY = "spring_returned_message_correlation";
    private final String DEFAULT_MESSAGE_ID_VALUE = "-1";

    /**
     * 执行方法
     * @param message
     */
    public void run(Message message){
        try {
            Mono.just(message).doOnNext(this::addRecord).subscribe(this::subscribe, this::consumerError);
        }catch (Exception e){
            log.error("RabbitmqConsumer error", e);
        }
    }

    /**
     * 获取rabbitmq队列名称
     * @return
     */
    protected abstract String getQueueName();

    /**
     * 获取IRabbitmqConsumerRecordService对象，用于存储消息日志
     * @return
     */
    protected abstract IRabbitmqConsumerRecordService getRabbitmqConsumerRecordService();

    /**
     * 捕获非ConverException的错误，需要可重写此方法
     * @param e
     */
    protected void consumerOtherError(Throwable e){}

    /**
     * 获取消息内容
     * @param message
     * @return
     */
    protected String getMessageBody(Message message) {
        try {
            return formatMessage(message.getBody());
        }catch (UnsupportedEncodingException e){
            log.error("Error get message:",e);
            throw new ConverException(message, "Error get message", e);
        }
    }

    private void addRecord(Message message){
        ParseMessage parseMessage = convertParseMessage(message);
        getRabbitmqConsumerRecordService().addRecord(getQueueName(), parseMessage.getMessageId(), parseMessage.getMessageBody());
    }

    private void subscribe(Message message){
        ParseMessage parseMessage = convertParseMessage(message);
        try {
            R result = apply(message);
            updateRecordResutl(parseMessage.getMessageId(), result);
        }catch (Exception e){
            log.error("rabbitmq subscribe error:{}", e);
            updateRecordResutl(parseMessage.getMessageId(), e.getMessage());
        }
    }

    private void consumerError(Throwable e){
        if(e instanceof ConverException) {
            updateRecordResutl(getMessageId(((ConverException) e).getBody()), e.getMessage());
        }else {
            consumerOtherError(e);
        }
    }

    private void updateRecordResutl(String messageId, Object result) {
        String resultString = "";
        if (null != result) {
            if (result instanceof String) {
                resultString = (String) result;
            } else {
                resultString = JSONObject.toJSONString(result);
            }
        }
        getRabbitmqConsumerRecordService().updateResult(messageId, resultString);
    }

    private String formatMessage(byte[] messageBody) throws UnsupportedEncodingException {
        return formatMessage(new String(messageBody, "UTF-8"));
    }

    private String formatMessage(String messageBody) {
        String message = messageBody;
        message = message.replace("\\", "");
        if (message.startsWith("\"")) {
            message = message.substring(1, message.length());
        }
        if (message.endsWith("\"")) {
            message = message.substring(0, message.length() - 1);
        }
        return message;
    }

    private ParseMessage convertParseMessage(Message message) {
        String messageId = getMessageId(message);
        String messageBody = null;
        try {
            messageBody = formatMessage(message.getBody());
        } catch (UnsupportedEncodingException e) {
            throw new ConverException(message, "Failed to convert byte to string", e);
        }
        return new ParseMessage(messageId, messageBody);
    }

    private String getMessageId(Message message){
        return (String) message.getMessageProperties().getHeaders().getOrDefault(MESSAGE_ID_KEY, DEFAULT_MESSAGE_ID_VALUE);
    }
}
