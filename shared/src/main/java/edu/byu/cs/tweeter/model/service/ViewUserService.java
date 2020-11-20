package edu.byu.cs.tweeter.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

public interface ViewUserService {
    /**
     * Returns the user specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the user to be viewed.
     */
    ViewUserResponse viewUser(ViewUserRequest request) throws IOException, TweeterRemoteException;
}
