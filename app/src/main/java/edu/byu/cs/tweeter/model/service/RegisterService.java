package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * Contains the business logic to support the register operation.
 */
public class RegisterService extends Service {
    public LoginRegisterResponse register(RegisterRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        LoginRegisterResponse response = serverFacade.register(request);

        if(response.isSuccess()) {
            loadImage(response.getUser());
        }

        return response;
    }
}
