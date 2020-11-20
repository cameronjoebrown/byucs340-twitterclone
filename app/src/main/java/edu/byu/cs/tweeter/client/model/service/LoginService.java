package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginService extends Service {

    private static final String URL_PATH = "/login";

    public LoginRegisterResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        LoginRegisterResponse response = serverFacade.login(request, URL_PATH);

        if(response.isSuccess()) {
            loadImage(response.getUser());
        }

        return response;
    }
}
