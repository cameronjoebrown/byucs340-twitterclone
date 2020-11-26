package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.ViewUserService;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

/**
 * Contains the business logic to support the view user operation
 */
public class ViewUserServiceProxy extends Service implements ViewUserService {

    private static final String URL_PATH = "/getuser";

    /**
     * Returns the user specified in the request
     *
     * @param request contains the data required to fulfill the request.
     * @return the user
     */
    @Override
    public ViewUserResponse viewUser(ViewUserRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        ViewUserResponse response = serverFacade.viewUser(request, URL_PATH);
        if(response.isSuccess()) {
            loadImage(response.getUser());
        }
        return response;

    }
}
