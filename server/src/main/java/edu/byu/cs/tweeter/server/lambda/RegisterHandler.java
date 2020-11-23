package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.server.service.RegisterServiceImpl;

/**
 * An AWS lambda function to register a user
 */
public class RegisterHandler implements RequestHandler<RegisterRequest, LoginRegisterResponse> {

    @Override
    public LoginRegisterResponse handleRequest(RegisterRequest request, Context context) {
        RegisterServiceImpl service = new RegisterServiceImpl();
        System.out.println(request.getFirstName());
        System.out.println(request.getUsername());
        return service.register(request);
    }
}
