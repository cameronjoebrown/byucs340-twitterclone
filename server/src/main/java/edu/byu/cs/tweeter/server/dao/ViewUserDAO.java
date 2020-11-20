package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

/**
 * A DAO for accessing 'view user' data from the database.
 */
public class ViewUserDAO {
    /**
     * This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    private FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

    public ViewUserResponse viewUser(ViewUserRequest request) {
        User currentUser = request.getCurrentUser();
        boolean isCurrentUser = false;
        boolean areFollowing = false;
        User requestedUser = null;
        if(request.getRequestedAlias().equals(currentUser.getAlias())) {
            isCurrentUser = true;
            requestedUser = currentUser;
        }
        else {
            for(User u : getFollowGenerator().getAllUsers()) {
                if(u.getAlias().equals(request.getRequestedAlias())) {
                    requestedUser = u;
                    break;
                }
            }
        }

        if(requestedUser != null) {
            for(Follow f : getFollowGenerator().getFollows()) {
                if (requestedUser.getAlias().equals(f.getFollowee().getAlias()) &&
                        currentUser.getAlias().equals(f.getFollower().getAlias())) {
                    areFollowing = true;
                    break;
                }
            }
            return new ViewUserResponse(requestedUser, isCurrentUser, areFollowing);
        }
        else {
            return new ViewUserResponse("Unable to find that user.");
        }
    }
}
