package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * Contains the business logic for getting the statuses of all users that a user follows
 */
public class FeedServiceProxy extends Service implements FeedService {

    private static final String URL_PATH = "/getfeed";
    /**
     * Returns the statuses of all users that the user specified in the request is following. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the statuses from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */

    @Override
    public FeedStoryResponse getFeed(FeedStoryRequest request) throws IOException, TweeterRemoteException {
        FeedStoryResponse response = getServerFacade().getFeed(request, URL_PATH);

        if(response.isSuccess()) {
            loadStatusImages(response.getStatuses());
        }

        return response;
    }
}
