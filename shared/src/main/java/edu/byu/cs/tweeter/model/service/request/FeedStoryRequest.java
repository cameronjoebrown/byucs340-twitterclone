package edu.byu.cs.tweeter.model.service.request;
/**
 * Contains all the information needed to make a request to have the server return the next page of
 * statuses for a specified user.
 */
public class FeedStoryRequest {

    private String username;
    private int limit;
    private String lastStatus;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FeedStoryRequest() {}

    /**
     * Creates an instance.
     *
     * @param username the user whose feed/story is requested
     * @param limit the maximum number of statuses to return
     * @param lastStatus the last status that was returned in the previous request (null if
     *                   there was no previous request or if no statuses were returned in the
     *                   previous request).
     *
     */
    public FeedStoryRequest(String username, int limit, String lastStatus) {
        this.username = username;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    /**
     * Returns the user whose statuses are to be returned by this request.
     *
     * @return the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the number representing the maximum number of statuses to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
    /**
     * Returns the last status that was returned in the previous request or null if there was no
     * previous request or if no statuses were returned in the previous request.
     *
     * @return the last status.
     */
    public String getLastStatus() {
        return lastStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }
}
