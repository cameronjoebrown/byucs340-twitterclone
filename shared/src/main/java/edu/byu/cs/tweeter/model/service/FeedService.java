package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * Defines the interface for the 'feed' service.
 */
public interface FeedService {

    /**
     * Returns the statuses of the user specified in the request and any other users that
     * they are following. Uses information in the request object to limit the number
     * of followees returned and to return the next set of followees after any that
     * were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    FeedStoryResponse getFeed(FeedStoryRequest request) throws IOException, TweeterRemoteException;
}
