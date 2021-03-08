package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginService extends UserService {
    LoginRegisterResponse response;
    LoginRequest request;

    public LoginRegisterResponse login(LoginRequest request) throws IOException {
        this.request = request;
        runService();
        return response;
    }

    @Override
    boolean runMethod(ServerFacade serverFacade) {
        this.response = serverFacade.login(request);
        return response.isSuccess();
    }

    @Override
    void load() throws IOException {
        loadImage(response.getUser());
    }
}
