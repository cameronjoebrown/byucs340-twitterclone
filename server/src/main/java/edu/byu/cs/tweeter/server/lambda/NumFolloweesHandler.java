package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;
import edu.byu.cs.tweeter.server.service.FollowingServiceImpl;

/**
 * An AWS lambda function that returns the users that are following a user
 */
public class NumFolloweesHandler implements RequestHandler<NumFollowsRequest, NumFollowsResponse> {

    /**
     * Returns the users that are following the user specified in the request. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followers.
     */
    @Override
    public NumFollowsResponse handleRequest(NumFollowsRequest request, Context context) {
        FollowingServiceImpl service = new FollowingServiceImpl();
        return service.getNumFollowees(request);
    }
}
