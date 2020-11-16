package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * Contains the business logic for getting the statuses of all users that a user follows
 */
public class FeedService extends Service {
    /**
     * Returns the statuses of all users that the user specified in the request is following. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the statuses from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    public FeedStoryResponse getFeed(FeedStoryRequest request) throws IOException {
        FeedStoryResponse response = getServerFacade().getFeed(request);

        if(response.isSuccess()) {
            loadStatusImages(response.getStatuses());
        }

        return response;
    }
}
