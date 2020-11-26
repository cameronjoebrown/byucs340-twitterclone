package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;
import edu.byu.cs.tweeter.server.service.ViewUserServiceImpl;

/**
 * An AWS lambda function that returns a user that was requested to be viewed
 */
public class ViewUserHandler implements RequestHandler<ViewUserRequest, ViewUserResponse> {

    @Override
    public ViewUserResponse handleRequest(ViewUserRequest request, Context context) {
        ViewUserServiceImpl service = new ViewUserServiceImpl();
        return service.viewUser(request);
    }
}
