package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginService extends Service {

    public LoginRegisterResponse login(LoginRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        LoginRegisterResponse response = serverFacade.login(request);

        if(response.isSuccess()) {
            loadImage(response.getUser());
        }

        return response;
    }
}
