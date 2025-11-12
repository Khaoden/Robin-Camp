package com.robincamp.movierating.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    private Auth auth = new Auth();
    private BoxOffice boxOffice = new BoxOffice();

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public BoxOffice getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BoxOffice boxOffice) {
        this.boxOffice = boxOffice;
    }

    public static class Auth {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class BoxOffice {
        private String url;
        private String apiKey;
        private int timeout = 5000;
        private Retry retry = new Retry();

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public Retry getRetry() {
            return retry;
        }

        public void setRetry(Retry retry) {
            this.retry = retry;
        }

        public static class Retry {
            private int maxAttempts = 3;
            private long backoff = 1000;

            public int getMaxAttempts() {
                return maxAttempts;
            }

            public void setMaxAttempts(int maxAttempts) {
                this.maxAttempts = maxAttempts;
            }

            public long getBackoff() {
                return backoff;
            }

            public void setBackoff(long backoff) {
                this.backoff = backoff;
            }
        }
    }
}
