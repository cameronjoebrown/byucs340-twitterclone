package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Contains the business logic to support the logout operation.
 */
public class LogoutServiceProxy extends Service implements LogoutService {
    private static final String URL_PATH = "/logout";


    @Override
    public Response logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();

        return serverFacade.logout(request, URL_PATH);
    }
}
