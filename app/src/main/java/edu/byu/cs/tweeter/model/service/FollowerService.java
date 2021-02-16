package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;

/**
 * Contains the business logic for getting the followers of a user.
 */
public class FollowerService extends Service {
    /**
     * Returns the followers of the user specified. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followers from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    public FollowerResponse getFollowers(FollowerRequest request) throws IOException {
        FollowerResponse response = getServerFacade().getFollowers(request);

        if(response.isSuccess()) {
            loadImages(response.getFollowers());
        }

        return response;
    }

    public NumFollowsResponse getNumFollowers(NumFollowsRequest request) {
        return getServerFacade().getNumFollowers(request);
    }
}
