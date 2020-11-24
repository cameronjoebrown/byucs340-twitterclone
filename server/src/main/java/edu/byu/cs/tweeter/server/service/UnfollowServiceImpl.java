package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.dao.UnfollowDAO;

/**
 * Contains the business logic for following or unfollowing a user.
 */
public class UnfollowServiceImpl implements UnfollowService {
    @Override
    public Response unfollow(NumFollowsRequest request) {
        return getUnfollowDAO().unfollow(request);
    }

    /**
     * Returns an instance of {@link UnfollowDAO}. Allows mocking of the DAO class
     * for testing purposes. All usages of DAO should get their DAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UnfollowDAO getUnfollowDAO() {
        return new UnfollowDAO();
    }
}
