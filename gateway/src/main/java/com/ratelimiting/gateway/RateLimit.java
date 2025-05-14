package com.ratelimiting.gateway;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "rate_limits")
public class RateLimit {

    @Id
    private String id;
    private String clientIpAddress;
    private String customParameter;
    private int requestCount;
    private Instant lastRequestTime;
    private String endPointPath;

    // Constructors, getters, and setters
    public RateLimit(String clientIpAddress, String customParameter, int requestCount,
            Instant lastRequestTime, String endPointPath) {
        this.clientIpAddress = clientIpAddress;
        this.customParameter = customParameter;
        this.requestCount = requestCount;
        this.lastRequestTime = lastRequestTime;
        this.endPointPath = endPointPath;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public Instant getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(Instant lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public String getCustomParameter() {
        return customParameter;
    }

    public void setCustomParameter(String customParameter) {
        this.customParameter = customParameter;
    }

    public String getEndPointPath() {
        return endPointPath;
    }

    public void setEndPointPath(String endPointPath) {
        this.endPointPath = endPointPath;
    }

}
