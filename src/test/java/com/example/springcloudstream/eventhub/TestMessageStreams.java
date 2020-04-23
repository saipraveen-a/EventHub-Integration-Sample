package com.example.springcloudstream.eventhub;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface TestMessageStreams {
  String INGESTIONSTATUS = "ingestionstatusconsumer";
  String INGESTIONSTATUSERRORS = "ingestionstatusconsumer";

  @Input(INGESTIONSTATUS)
  SubscribableChannel ingestionStatusChannel();


  @Input(INGESTIONSTATUSERRORS)
  SubscribableChannel ingestionStatusErrorsChannel();
}