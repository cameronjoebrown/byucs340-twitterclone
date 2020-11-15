package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a follow/unfollow request.
 */
public class FollowUnfollowRequest {

    private final User followee;
    private final User follower;

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
}
