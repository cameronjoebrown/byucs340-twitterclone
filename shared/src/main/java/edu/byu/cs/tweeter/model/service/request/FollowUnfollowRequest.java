package edu.byu.cs.tweeter.model.service.request;


import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a follow/unfollow request.
 */
public class FollowUnfollowRequest {

    private User followee;
    private User follower;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowUnfollowRequest() {}

    /**
     * Creates an instance.
     *
     * @param followee the {@link User} who is to be followed or unfollowed
     * @param follower the {@link User} who is following or unfollowing the followee
     */
    public FollowUnfollowRequest(User followee, User follower) {
        this.followee = followee;
        this.follower = follower;
    }

    /**
     * Returns the followee who will be followed or unfollowed
     *
     * @return the followee.
     */
    public User getFollowee() {
        return followee;
    }

    /**
     * Returns the follower who is going to follow or unfollow a user
     *
     * @return the follower.
     */
    public User getFollower() {
        return follower;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }
}
