package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Defines the interface for the 'follow' service.
 */
public interface FollowService {

    /**
     * Current user follows the user specified in the request
     *
     * @param request contains the data required to fulfill the request.
     * @return message of whether or not the user was followed
     */
    Response follow(NumFollowsRequest request) throws IOException, TweeterRemoteException;

}
