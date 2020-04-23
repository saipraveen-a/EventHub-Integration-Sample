package com.example.springcloudstream.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlobstoreNotificationEventHandler {
  @StreamListener(MessageStreams.BLOBNOTIFICATION)
  public void handleBlobNotification(@Payload String message) {
    log.info("New blob notification message received: '{}'", message);
  }
}