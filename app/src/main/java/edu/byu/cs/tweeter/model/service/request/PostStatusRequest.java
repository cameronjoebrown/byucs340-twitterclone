package edu.byu.cs.tweeter.model.service.request;

import java.time.LocalDateTime;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to post a status.
 */
public class PostStatusRequest {

    private final String statusText;
    private final User user;
    private final LocalDateTime timeStamp;

    /**
     * Creates an instance.
     *
     * @param statusText the text of the status to be created
     * @param user the user whose status it is
     * @param timeStamp the time that the status was created
     */
    PostStatusRequest(String statusText, User user, LocalDateTime timeStamp) {
        this.statusText = statusText;
        this.user = user;
        this.timeStamp = timeStamp;
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
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

}
