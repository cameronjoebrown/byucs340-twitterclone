package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * Contains the business logic to support the register operation.
 */
public class RegisterService extends UserService {
    LoginRegisterResponse response;
    RegisterRequest request;

    public LoginRegisterResponse register(RegisterRequest request) throws IOException {
        this.request = request;
        runService();
        return response;
    }

    @Override
    boolean runMethod(ServerFacade serverFacade) {
        this.response = serverFacade.register(request);
        return response.isSuccess();
    }

    @Override
    void load() throws IOException {
        loadImage(response.getUser());
    }
}
