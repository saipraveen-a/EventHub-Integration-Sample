package com.example.springcloudstream.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@Slf4j
public abstract class EventHandlerTest<T> {
  protected <T> void processHeaders(Message<T> message) {
    System.out.println("Received tenant id from message: " + message.getHeaders().get("tenant-id"));
    System.out.println("Received tenant alias from message: " + message.getHeaders().get("tenant-alias"));
  }

  abstract void handleEvent(Message<T> message) throws Exception;
}
