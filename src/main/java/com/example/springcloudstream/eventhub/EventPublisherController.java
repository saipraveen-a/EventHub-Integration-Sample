package com.example.springcloudstream.eventhub;

import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventPublisherController {
  private final MessageStreams messageStreams;

  public EventPublisherController(MessageStreams messageStreams) {
    this.messageStreams = messageStreams;
  }

  @GetMapping("/")
  public String sendForm() {
    return "<html><body>" +
        "<form action=\"/messages\" method=\"post\">" +
        "<input type=\"text\" name=\"text\">" +
        "<input type=\"submit\">" +
        "</form></body><html>";
  }

  @PostMapping("/messages")
  public String sendMessage(@RequestBody String message) {
    this.messageStreams.ingestionStatusChannel().send(new GenericMessage<>(message));
    return message;
  }
}