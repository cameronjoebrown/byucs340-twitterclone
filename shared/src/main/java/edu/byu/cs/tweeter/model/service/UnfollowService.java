package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Defines the interface for the 'unfollow' service.
 */
public interface UnfollowService {

    /**
     * Current user unfollows the user specified in the request
     *
     * @param request contains the data required to fulfill the request.
     * @return message of whether or not the user was unfollowed
     */
    Response unfollow(NumFollowsRequest request) throws IOException, TweeterRemoteException;
}
