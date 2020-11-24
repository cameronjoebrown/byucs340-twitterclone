package edu.byu.cs.tweeter.model.service.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to post a status.
 */
public class PostStatusRequest {

    private String statusText;
    private User user;
    private String timeStamp;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private PostStatusRequest() {}

    /**
     * Creates an instance.
     *
     * @param statusText the text of the status to be created
     * @param user the user whose status it is
     * @param timeStamp the time that the status was created
     */
    public PostStatusRequest(String statusText, User user, LocalDateTime timeStamp) {
        this.statusText = statusText;
        this.user = user;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.timeStamp = timeStamp.format(formatter);
    }


    /**
     * Returns the text of the status that is being created
     *
     * @return the text of the status
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * Returns the user whose status is being created
     *
     * @return the user of the status
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the time stamp of the status that is being created
     *
     * @return the time stamp of the status
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(this.timeStamp, formatter);
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.timeStamp = timeStamp.format(formatter);
    }
}
