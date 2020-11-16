package edu.byu.cs.tweeter.client.model.service;


import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * Contains the business logic to support the logout operation.
 */
public class LogoutService extends Service {
    public Response logout(LogoutRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();

        return serverFacade.logout(request);
    }
}
