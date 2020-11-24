package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceProxy extends Service implements FollowingService {

    static final String FOLLOWING_URL_PATH = "/getfollowing";
    static final String NUMFOLLOWERS_URL_PATH = "/getfollowing";


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */

    @Override
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException, TweeterRemoteException {
        FollowingResponse response = getServerFacade().getFollowees(request, FOLLOWING_URL_PATH);

        if(response.isSuccess()) {
            loadImages(response.getFollowees());
        }

        return response;
    }

    @Override
    public NumFollowsResponse getNumFollowees(NumFollowsRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getNumFollowees(request, NUMFOLLOWERS_URL_PATH);
    }

}
