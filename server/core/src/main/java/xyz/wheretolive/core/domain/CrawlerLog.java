package xyz.wheretolive.core.domain;

import java.util.Date;

public class CrawlerLog {

    private Date timestamp;

    private String message;

    private String crawlerName;

    private int executionTimeInSeconds;

    public int getExecutionTimeInSeconds() {
        return executionTimeInSeconds;
    }

    public void setExecutionTimeInSeconds(int executionTimeInSeconds) {
        this.executionTimeInSeconds = executionTimeInSeconds;
    }

    public String getCrawlerName() {
        return crawlerName;
    }

    public void setCrawlerName(String crawlerName) {
        this.crawlerName = crawlerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
