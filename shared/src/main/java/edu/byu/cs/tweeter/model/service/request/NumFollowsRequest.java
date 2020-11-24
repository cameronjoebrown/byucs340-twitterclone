package edu.byu.cs.tweeter.model.service.request;


/**
 * Contains all the information needed to make a follow/unfollow request.
 */
public class NumFollowsRequest {

    private String followee;
    private String follower;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private NumFollowsRequest() {}

    /**
     * Creates an instance.
     *
     * @param followee the user who is to be followed or unfollowed
     * @param follower the user who is following or unfollowing the followee
     */
    public NumFollowsRequest(String followee, String follower) {
        this.followee = followee;
        this.follower = follower;
    }

    /**
     * Returns the followee who will be followed or unfollowed
     *
     * @return the followee.
     */
    public String getFollowee() {
        return followee;
    }

    /**
     * Returns the follower who is going to follow or unfollow a user
     *
     * @return the follower.
     */
    public String getFollower() {
        return follower;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }
}
