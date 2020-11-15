package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Contains the business logic to support the follow operation
 */
public class FollowService extends Service {
    public Response follow(FollowUnfollowRequest request) {
        ServerFacade serverFacade = getServerFacade();

        return serverFacade.follow(request);
    }
}
