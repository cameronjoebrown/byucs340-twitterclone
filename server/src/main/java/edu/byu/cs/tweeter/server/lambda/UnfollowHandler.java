package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.service.UnfollowServiceImpl;

/**
 * An AWS lambda function that removes a user to a user's following
 */
public class UnfollowHandler implements RequestHandler<FollowUnfollowRequest, Response> {

    @Override
    public Response handleRequest(FollowUnfollowRequest request, Context context) {
        UnfollowServiceImpl service = new UnfollowServiceImpl();
        return service.unfollow(request);
    }
}
