package edu.byu.cs.tweeter.model.service.response;


import edu.byu.cs.tweeter.model.domain.User;

public class ViewUserResponse extends Response {

    private User user;
    private Boolean isFollowing;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public ViewUserResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the user to be viewed
     * @param isFollowing boolean showing if the logged in user is following the requested user
     */
    public ViewUserResponse(User user, Boolean isFollowing) {
        super(true, null);
        this.user = user;
        this.isFollowing = isFollowing;
    }

    /**
     * Returns the requested user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the boolean specifying whether the logged in user is following
     * the requested user or not
     *
     * @return the boolean.
     */
    public Boolean getFollowing() {
        return isFollowing;
    }
}
