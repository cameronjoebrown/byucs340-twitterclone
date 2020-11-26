package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Defines the interface for the 'logout' service.
 */
public interface LogoutService {

    /**
     * Logout a user
     *
     * @param request contains the data required to fulfill the request.
     * @return a message of whether the user was logged out or not
     */
    Response logout(LogoutRequest request) throws IOException, TweeterRemoteException;

}
