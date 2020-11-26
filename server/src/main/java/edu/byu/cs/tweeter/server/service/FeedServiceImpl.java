package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

/**
 * Contains the business logic for getting the statuses of user and all users they are following.
 */
public class FeedServiceImpl implements FeedService {
    @Override
    public FeedStoryResponse getFeed(FeedStoryRequest request){
        return getFeedDAO().getFeed(request);
    }

    /**
     * Returns an instance of {@link FeedDAO}. Allows mocking of the DAO class
     * for testing purposes. All usages of DAO should get their DAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
