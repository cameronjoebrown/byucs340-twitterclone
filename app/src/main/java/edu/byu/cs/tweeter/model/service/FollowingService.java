package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingService extends UserService {
    FollowingResponse response;
    FollowingRequest request;
    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the followees from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) throws IOException {
        this.request = request;
        runService();
        return response;
    }

    public NumFollowsResponse getNumFollowees(NumFollowsRequest request) {
        return getServerFacade().getNumFollowees(request);
    }

    @Override
    boolean runMethod(ServerFacade serverFacade) {
        this.response = serverFacade.getFollowees(request);
        return response.isSuccess();
    }

    @Override
    void load() throws IOException {
        for(User user : response.getFollowees()) {
            loadImage(user);
        }
    }
}
