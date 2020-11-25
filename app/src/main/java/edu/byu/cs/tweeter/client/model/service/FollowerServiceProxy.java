package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowerService;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;

/**
 * Contains the business logic for getting the followers of a user.
 */
public class FollowerServiceProxy extends Service implements FollowerService {

    private static final String URL_PATH = "/getfollower";
    private static final String NUMFOLLOWERS_URL_PATH = "/numfollowers";

    /**
     * Returns the followers of the user specified. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    @Override
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException, TweeterRemoteException {
        FollowerResponse response = getServerFacade().getFollowers(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response.getFollowers());
        }

        return response;
    }

    @Override
    public NumFollowsResponse getNumFollowers(NumFollowsRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getNumFollowers(request, NUMFOLLOWERS_URL_PATH);
    }
}
