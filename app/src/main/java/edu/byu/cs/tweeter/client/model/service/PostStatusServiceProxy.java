package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * Contains the business logic to support the post status operation
 */
public class PostStatusServiceProxy extends Service implements PostStatusService {

    private static final String URL_PATH = "/poststatus";

    /**
     * Returns the result of the post status operation
     *
     * @param request contains the data required to fulfill the request.
     * @return the result of post status
     */
    @Override
    public PostStatusResponse postStatus(PostStatusRequest request)
            throws IOException, TweeterRemoteException {

        return getServerFacade().postStatus(request, URL_PATH);
    }
}
