package edu.byu.cs.tweeter.model.service.request;


/**
 * Contains all the information needed to make a follow/unfollow request.
 */
public class NumFollowsRequest {

    private String username;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private NumFollowsRequest() {}

    public NumFollowsRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
