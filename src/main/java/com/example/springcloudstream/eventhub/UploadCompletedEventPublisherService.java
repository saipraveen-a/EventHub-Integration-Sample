package com.example.springcloudstream.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@Slf4j
public class UploadCompletedEventPublisherService {
  private final MessageStreams messageStreams;

  public UploadCompletedEventPublisherService(MessageStreams messageStreams) {
    this.messageStreams = messageStreams;
  }

  public void sendUploadCompletedEvent() {
    log.info("Sending upload completed event");
    MessageChannel messageChannel = messageStreams.uploadNotificationChannel();
    messageChannel.send(MessageBuilder
        .withPayload("Upload finished")
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
        .build());
  }
}