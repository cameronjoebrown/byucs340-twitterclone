package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;

/**
 * Contains the business logic for getting the followers of a user.
 */
public class FollowerService extends UserService {
    FollowerResponse response;
    FollowerRequest request;
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
        this.request = request;
        runService();
        return response;
    }

    public NumFollowsResponse getNumFollowers(NumFollowsRequest request) {
        return getServerFacade().getNumFollowers(request);
    }

    @Override
    boolean runMethod(ServerFacade serverFacade) {
        this.response = serverFacade.getFollowers(request);
        return response.isSuccess();
    }

    @Override
    void load() throws IOException {
        for(User user : response.getFollowers()) {
            loadImage(user);
        }
    }
}
