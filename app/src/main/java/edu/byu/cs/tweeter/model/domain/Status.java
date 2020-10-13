package edu.byu.cs.tweeter.model.domain;

import java.time.LocalDateTime;

/**
 * Represents a status
 */
public class Status {

    private final String statusText;
    private final User user;
    private final LocalDateTime timeStamp;

    public Status(String statusText, User user, LocalDateTime timeStamp) {
        this.statusText = statusText;
        this.user = user;
        this.timeStamp = timeStamp;
    }

    public String getStatusText() {
        return statusText;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
