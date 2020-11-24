package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Contains the business logic to support the unfollow operation
 */
public class UnfollowServiceProxy extends Service implements UnfollowService {
    private static final String URL_PATH = "/unfollow";

    @Override
    public Response unfollow(NumFollowsRequest request) throws IOException, TweeterRemoteException {

        return getServerFacade().unfollow(request, URL_PATH);
    }
}
