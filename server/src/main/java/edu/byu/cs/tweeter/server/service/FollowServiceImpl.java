package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.dao.FollowDAO;

/**
 * Contains the business logic for following or unfollowing a user.
 */
public class FollowServiceImpl implements FollowService {
    @Override
    public Response follow(FollowUnfollowRequest request) {
        return getFollowDAO().follow(request);
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
