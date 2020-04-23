package com.example.springcloudstream.eventhub;

import com.example.eventhub.EventPublisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IngestionStatusEventPublisher extends EventPublisher<IngestionStatusMessage> {
  private final MessageStreams messageStreams;

  public IngestionStatusEventPublisher(MessageStreams messageStreams) {
    this.messageStreams = messageStreams;
  }

  @Override
  protected boolean isTenantAware() {
    return true;
  }

  @Override
  protected MessageChannel getChannel() {
    return messageStreams.ingestionStatusChannel();
  }
}