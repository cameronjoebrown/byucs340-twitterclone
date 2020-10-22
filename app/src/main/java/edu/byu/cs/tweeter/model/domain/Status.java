package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a status
 */
public class Status implements Comparable<Status>, Serializable {

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

    @Override
    public int compareTo(Status status) {
        return status.getTimeStamp().compareTo(this.getTimeStamp());
    }

}
