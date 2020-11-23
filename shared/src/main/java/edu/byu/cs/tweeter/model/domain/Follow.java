package edu.byu.cs.tweeter.model.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a follow relationship.
 */
public class Follow {

    private User follower;
    private User followee;

    /**
     * Allows construction of the object from Json. Private so it won't be called by other code.
     */
    private Follow() {}

    public Follow(@NotNull User follower, @NotNull User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Follow that = (Follow) o;
        return follower.equals(that.follower) &&
                followee.equals(that.followee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(follower, followee);
    }

    @NotNull
    @Override
    public String toString() {
        return "Follow{" +
                "follower=" + follower.getUsername() +
                ", followee=" + followee.getUsername() +
                '}';
    }
}
