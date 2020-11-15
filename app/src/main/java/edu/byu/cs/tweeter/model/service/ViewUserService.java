package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

/**
 * Contains the business logic to support the view user operation
 */
public class ViewUserService extends Service {
    /**
     * Returns the user specified in the request
     *
     * @param request contains the data required to fulfill the request.
     * @return the user
     */
    public ViewUserResponse viewUser(ViewUserRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        ViewUserResponse response = serverFacade.viewUser(request);
        if(response.isSuccess()) {
            loadImage(response.getUser());
        }
        return response;

    }
}
