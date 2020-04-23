package com.example.springcloudstream.eventhub;

import com.example.eventhub.EventHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class IngestionStatusEventHandler extends EventHandler<IngestionStatusMessage> {
  private AtomicInteger eventCount = new AtomicInteger();

  @StreamListener(TestMessageStreams.INGESTIONSTATUS)
  @Override
  public void handleEvent(Message<IngestionStatusMessage> message) throws Exception {
    processHeaders(message);
    if (Integer.valueOf(message.getHeaders().get("deliveryAttempt").toString()) == 1) {
      eventCount.incrementAndGet();
      log.info("Event Count is {} ", eventCount.get());
      log.info("New ingestion status event received: {}", message.getPayload().getCorrelationId());
    }
    if (eventCount.get() % 5 == 0)
      throw new Exception();

    // process the event
  }

  public int getEventCount() {
    return eventCount.get();
  }

  @ServiceActivator(inputChannel = "ingestion-status.testconsumergroup.errors")
  public void handleError(ErrorMessage message) {

    Message originalMessage = message.getOriginalMessage();
    byte[] payload = (byte[])originalMessage.getPayload();
    System.out.println("Handling ERROR Message: " + new String(payload));
  }

  @StreamListener("errorChannel")
  public void error(ErrorMessage message) {
    Message originalMessage = message.getOriginalMessage();
    byte[] payload = (byte[])originalMessage.getPayload();
    System.out.println("Handling ERROR Message: " + new String(payload));
  }
}