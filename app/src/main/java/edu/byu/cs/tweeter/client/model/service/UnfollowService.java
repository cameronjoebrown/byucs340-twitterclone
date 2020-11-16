package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Contains the business logic to support the unfollow operation
 */
public class UnfollowService extends Service {
    public Response unfollow(FollowUnfollowRequest request) {
        ServerFacade serverFacade = getServerFacade();

        return serverFacade.unfollow(request);
    }
}
