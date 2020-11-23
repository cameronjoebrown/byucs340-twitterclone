package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a status
 */
public class Status implements Comparable<Status>, Serializable {

    private String statusText;
    private User user;
    private LocalDateTime timeStamp;

    /**
     * Allows construction of the object from Json. Private so it won't be called by other code.
     */
    private Status() {}

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

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public int compareTo(Status status) {
        return status.getTimeStamp().compareTo(this.getTimeStamp());
    }

}
