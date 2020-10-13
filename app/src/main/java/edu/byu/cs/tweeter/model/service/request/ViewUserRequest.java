package edu.byu.cs.tweeter.model.service.request;

public class ViewUserRequest {

    private final String username;

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     */
    ViewUserRequest(String username) {
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
