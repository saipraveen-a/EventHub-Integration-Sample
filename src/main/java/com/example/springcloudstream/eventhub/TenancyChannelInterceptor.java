package com.example.springcloudstream.eventhub;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

public class TenancyChannelInterceptor implements ChannelInterceptor {
  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    message.getHeaders().put("tenant-id", "some-tenant");
    return message;
  }

  @Override
  public Message<?> postReceive(Message<?> message, MessageChannel channel) {
    System.out.println("Received header : " + message.getHeaders().get("tenant-id"));
    return message;
  }
}
