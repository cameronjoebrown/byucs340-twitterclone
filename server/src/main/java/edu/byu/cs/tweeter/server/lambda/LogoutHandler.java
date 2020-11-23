package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.service.LogoutServiceImpl;

/**
 * An AWS lambda function that logs a user out of their account
 */
public class LogoutHandler implements RequestHandler<LogoutRequest, Response> {

    @Override
    public Response handleRequest(LogoutRequest request, Context context) {
        LogoutServiceImpl service = new LogoutServiceImpl();
        return service.logout(request);
    }
}
