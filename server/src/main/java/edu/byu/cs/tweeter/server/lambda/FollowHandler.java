package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.service.FollowServiceImpl;

/**
 * An AWS lambda function that adds a user to a user's following
 */
public class FollowHandler implements RequestHandler<NumFollowsRequest, Response> {
    @Override
    public Response handleRequest(NumFollowsRequest request, Context context) {
        FollowServiceImpl service = new FollowServiceImpl();
        return service.follow(request);
    }
}
