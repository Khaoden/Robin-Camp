package com.robincamp.movierating.client;

import com.robincamp.movierating.config.AppConfig;
import com.robincamp.movierating.dto.BoxOfficeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class BoxOfficeClient {

    private static final Logger logger = LoggerFactory.getLogger(BoxOfficeClient.class);

    private final RestTemplate restTemplate;
    private final AppConfig.BoxOffice config;

    public BoxOfficeClient(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.config = appConfig.getBoxOffice();
    }

    public BoxOfficeRecord fetchBoxOfficeData(String title) {
        if (config.getUrl() == null || config.getUrl().isEmpty() ||
                config.getApiKey() == null || config.getApiKey().isEmpty()) {
            logger.warn("Box office URL or API key not configured");
            return null;
        }

        try {
            String url = config.getUrl() + "/boxoffice?title=" +
                    java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8);

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", config.getApiKey());
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<BoxOfficeRecord> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    BoxOfficeRecord.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Successfully fetched box office data for: {}", title);
                return response.getBody();
            }

            return null;
        } catch (HttpClientErrorException.NotFound e) {
            logger.debug("Box office data not found for: {}", title);
            return null;
        } catch (HttpClientErrorException e) {
            logger.warn("Box office API returned error for {}: {}", title, e.getStatusCode());
            return null;
        } catch (ResourceAccessException e) {
            logger.warn("Box office API timeout/connection error for {}: {}", title, e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error fetching box office data for {}: {}", title, e.getMessage());
            return null;
        }
    }
}
