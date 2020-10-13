package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have
 * the server return the user to be viewed
 */
public class ViewUserRequest {

    private final String username;
    private final User loggedInUser;

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     */
    ViewUserRequest(String username, User loggedInUser) {
        this.username = username;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Returns the username of the user to be viewed by this request.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the current logged in user.
     *
     * @return the user.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }
}
