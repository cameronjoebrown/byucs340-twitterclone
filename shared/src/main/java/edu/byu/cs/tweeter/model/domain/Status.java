package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a status
 */
public class Status implements Comparable<Status>, Serializable {

    private String statusText;
    private User user;
    private String timeStamp;

    /**
     * Allows construction of the object from Json. Private so it won't be called by other code.
     */
    private Status() {}

    public Status(String statusText, User user, LocalDateTime timeStamp) {
        this.statusText = statusText;
        this.user = user;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.timeStamp = timeStamp.format(formatter);
    }

    public String getStatusText() {
        return statusText;
    }

    public User getUser() {
        return user;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.timeStamp = timeStamp.format(formatter);
    }

    @Override
    public int compareTo(Status status) {
        return status.getTimeStamp().compareTo(this.getTimeStamp());
    }

}
