package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.PostStatusDAO;


/**
 * Contains the business logic for creating a status.
 */
public class PostStatusServiceImpl implements PostStatusService {
    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) {
        return getPostStatusDAO().postStatus(request);
    }

    /**
     * Returns an instance of {@link PostStatusDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    PostStatusDAO getPostStatusDAO() {
        return new PostStatusDAO();
    }
}
