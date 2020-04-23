package com.example.springcloudstream.eventhub;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MessageStreams {
  String WORKSPACE = "workspace";
  String UPLOADNOTIFICATION = "uploadnotification";
  String BLOBNOTIFICATION = "blobnotification";
  String INGESTIONSTATUS = "ingestionstatusproducer";

  @Input(WORKSPACE)
  SubscribableChannel workspaceChannel();

  @Output(UPLOADNOTIFICATION)
  MessageChannel uploadNotificationChannel();

  @Input(BLOBNOTIFICATION)
  SubscribableChannel blobNotificationChannel();

  @Output(INGESTIONSTATUS)
  MessageChannel ingestionStatusChannel();
}