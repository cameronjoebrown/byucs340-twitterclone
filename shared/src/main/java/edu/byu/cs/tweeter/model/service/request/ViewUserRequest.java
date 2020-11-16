package edu.byu.cs.tweeter.model.service.request;


/**
 * Contains all the information needed to make a request to have
 * the server return the user to be viewed
 */
public class ViewUserRequest {

    private final String username;

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     */
    public ViewUserRequest(String username) {
        this.username = username;
    }

    /**
     * Returns the username of the user to be viewed by this request.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

}
