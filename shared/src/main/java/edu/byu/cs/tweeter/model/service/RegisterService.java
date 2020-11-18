package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * Defines the interface for the 'login' service.
 */
public interface RegisterService {

    /**
     * Register a user
     *
     * @param request contains the data required to fulfill the request.
     * @return the user who registered and their auth token
     */
    LoginRegisterResponse registerUser(RegisterRequest request) throws IOException, TweeterRemoteException;
}
