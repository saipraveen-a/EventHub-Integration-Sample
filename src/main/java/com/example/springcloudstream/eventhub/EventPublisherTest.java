package com.example.springcloudstream.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public abstract class EventPublisherTest<T> {
  public boolean publish(T event) {
    MessageChannel messageChannel = getChannel();

    MessageBuilder messageBuilder = MessageBuilder.withPayload(event);
    if (isTenantAware()) {
      messageBuilder.setHeader("tenant-id", "some-tenant-id");
      messageBuilder.setHeader("tenant-alias", "some-tenant-alias");
    }

    boolean messageSent = messageChannel.send(messageBuilder.build());

    log.info("Event {} published with status {}", event, messageSent);

    return messageSent;
  }

  protected abstract boolean isTenantAware();

  protected abstract MessageChannel getChannel();
}
