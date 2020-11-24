package edu.byu.cs.tweeter.model.service.request;


/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followers for a specified followee.
 */
public class FollowerRequest {
    private String followee;
    private int limit;
    private String lastFollower;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowerRequest() {}

    /**
     * Creates an instance.
     *
     * @param followee the user whose followers are to be returned.
     * @param limit the maximum number of followers to return.
     * @param lastFollower the last follower that was returned in the previous request (null if
     *                     there was no previous request or if no followers were returned in the
     *                     previous request).
     */
    public FollowerRequest(String followee, int limit, String lastFollower) {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    /**
     * Returns the followee whose followers are to be returned by this request.
     *
     * @return the followee.
     */
    public String getFollowee() {
        return followee;
    }

    /**
     * Returns the number representing the maximum number of followers to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last follower that was returned in the previous request or null if there was no
     * previous request or if no followers were returned in the previous request.
     *
     * @return the last follower.
     */
    public String getLastFollower() {
        return lastFollower;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollower(String lastFollower) {
        this.lastFollower = lastFollower;
    }
}
