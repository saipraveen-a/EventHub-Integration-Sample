package com.example.springcloudstream.eventhub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkspaceEventHandler {
  @StreamListener(MessageStreams.WORKSPACE)
  public void handleWorkspaceEvent(@Payload String message) {
    log.info("New workspace message received: '{}'", message);
  }
}