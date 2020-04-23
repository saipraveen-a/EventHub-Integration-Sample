package com.example.springcloudstream.eventhub;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.example.eventhub.tenancy.TenancyContext;
import com.example.eventhub.tenancy.TenancyContextHolder;
import com.example.eventhub.tenancy.Tenant;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8184"})
@ContextConfiguration(classes = SpringCloudStreamEventHubApplication.class)
@ActiveProfiles(SpringCloudStreamEventHubApplication.Profiles.NO_DEPENDENCIES)
@Slf4j
public class SpringCloudStreamEventHubApplicationTests {

  @Autowired
  private IngestionStatusEventPublisher ingestionStatusEventPublisherService;

  @Autowired
  private IngestionStatusEventHandler ingestionStatusEventHandler;

  @Test
  void testIngestionStatusPublisherService() throws InterruptedException {
    TenancyContextHolder.setContext(TenancyContext.newContext(new Tenant("tenant-id", "tenant-alias", true)));

    for (int i = 0; i < 10; i++) {
      ingestionStatusEventPublisherService.publish(givenIngestionStatusMessage("" + i));
    }

    Thread.sleep(40000);

    assertThat(ingestionStatusEventHandler.getEventCount(), is(10));
  }

  private IngestionStatusMessage givenIngestionStatusMessage() {
    return IngestionStatusMessage.builder()
        .correlationId("some-correlation-id")
        .status("done")
        .source("some-source")
        .eventTime(OffsetDateTime.now())
        .build();
  }

  private IngestionStatusMessage givenIngestionStatusMessage(String id) {
    return IngestionStatusMessage.builder()
        .correlationId(id)
        .status("done")
        .source("some-source")
        .eventTime(OffsetDateTime.now())
        .build();
  }

  @Test
  void testConcurrency() throws InterruptedException {

    ExecutorService executorService = Executors.newFixedThreadPool(20);
    IntStream.range(0, 9999).boxed().forEach(
        (i) -> executorService.submit(new IngestionStatusPublisherRunnable(i, ingestionStatusEventPublisherService)));

    Thread.sleep(40000);

    assertThat(ingestionStatusEventHandler.getEventCount(), is(10000));
  }

  private class IngestionStatusPublisherRunnable implements Runnable {
    IngestionStatusEventPublisher ingestionStatusEventPublisherService;
    String id;

    public IngestionStatusPublisherRunnable(
        int id,
        IngestionStatusEventPublisher ingestionStatusEventPublisherService) {
      this.id = "" + id;
      this.ingestionStatusEventPublisherService = ingestionStatusEventPublisherService;
    }

    @Override
    public void run() {
      TenancyContextHolder.setContext(TenancyContext.newContext(new Tenant("tenant-id", "tenant-alias", true)));

      log.info("Sending ingestion status event in Thread: status-{}", id);
      try {
        ingestionStatusEventPublisherService.publish(givenIngestionStatusMessage(id));
      } catch (Exception e) {
        log.error("Exception while sending ingestion status event", e);
      }
    }
  }
}
