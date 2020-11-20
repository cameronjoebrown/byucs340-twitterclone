package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * Contains the business logic to support the register operation.
 */
public class RegisterServiceProxy extends Service implements RegisterService {

    private static final String URL_PATH = "/register";

    @Override
    public LoginRegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        LoginRegisterResponse response = serverFacade.register(request, URL_PATH);

        if(response.isSuccess()) {
            loadImage(response.getUser());
        }

        return response;
    }
}
