package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * Defines the interface for the 'create status' service.
 */
public interface PostStatusService {

    /**
     * Returns the status that was created
     *
     * @param request contains the data required to fulfill the request.
     * @return the status that was created.
     */
    PostStatusResponse createStatus(PostStatusRequest request) throws IOException;

}
