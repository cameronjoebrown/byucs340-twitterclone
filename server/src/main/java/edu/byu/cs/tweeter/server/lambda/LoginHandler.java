package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.server.service.RegisterServiceImpl;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler implements RequestHandler<LoginRequest, LoginRegisterResponse> {

    @Override
    public LoginRegisterResponse handleRequest(LoginRequest loginRequest, Context context) {
        RegisterServiceImpl loginService = new RegisterServiceImpl();
        return loginService.login(loginRequest);
    }
}
