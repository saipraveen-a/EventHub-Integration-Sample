package com.example.springcloudstream.eventhub;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public final class RestTemplateFactory {
  private static final int MAX_CONNECTION = 10;
  private static final int MAX_CONNECTION_PER_ROUTE = 10;

  private RestTemplateFactory() {
    // utility class
  }

  public static RestTemplate createRestTemplate() {
    CloseableHttpClient httpClient = HttpClientBuilder.create()
        .setMaxConnTotal(MAX_CONNECTION)
        .setMaxConnPerRoute(MAX_CONNECTION_PER_ROUTE)
        .build();

    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    return restTemplate;
  }
}