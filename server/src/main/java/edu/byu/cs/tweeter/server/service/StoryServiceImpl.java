package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

/**
 * Contains the business logic for getting the statuses of a user.
 */
public class StoryServiceImpl implements StoryService {
    @Override
    public FeedStoryResponse getStory(FeedStoryRequest request) {
        return getStoryDAO().getStory(request);
    }

    /**
     * Returns an instance of {@link StoryDAO}. Allows mocking of the DAO class
     * for testing purposes. All usages of DAO should get their DAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
     StoryDAO getStoryDAO() {
        return new StoryDAO();
    }
}
