package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

/**
 * Contains the business logic for unfollowing a user.
 */
public class UnfollowServiceImpl implements UnfollowService {
    @Override
    public Response unfollow(FollowUnfollowRequest request) {
        return getFollowDAO().unfollow(request);
    }

    /**
     * Returns an instance of {@link FollowDAO}. Allows mocking of the DAO class
     * for testing purposes. All usages of DAO should get their DAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowDAO getFollowDAO() {
        return new FollowDAO();
    }
}
