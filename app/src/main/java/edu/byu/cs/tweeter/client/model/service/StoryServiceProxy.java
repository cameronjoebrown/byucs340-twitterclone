package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * Contains the business logic for getting the statuses of a user
 */
public class StoryServiceProxy extends Service implements StoryService {

    private static final String URL_PATH = "/getstory";

    /**
     * Returns the statuses that the user specified in the request has posted. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    @Override
    public FeedStoryResponse getStory(FeedStoryRequest request) throws IOException, TweeterRemoteException {
        FeedStoryResponse response = getServerFacade().getStory(request, URL_PATH);

        if(response.isSuccess()) {
            loadStatusImages(response.getStatuses());
        }

        return response;
    }
}
