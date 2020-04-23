package com.example.springcloudstream.eventhub;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(TestMessageStreams.class)
public class EventHubStreamsTestConfiguration {
}
