package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * Contains the business logic to support the post status operation
 */
public class PostStatusService extends Service {
    /**
     * Returns the result of the post status operation
     *
     * @param request contains the data required to fulfill the request.
     * @return the result of post status
     */
    public PostStatusResponse postStatus(PostStatusRequest request) {
        ServerFacade serverFacade = getServerFacade();

        return serverFacade.postStatus(request);
    }
}
