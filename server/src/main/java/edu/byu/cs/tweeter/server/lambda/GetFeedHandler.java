package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;
import edu.byu.cs.tweeter.server.service.FeedServiceImpl;

/**
 * An AWS lambda function that returns the statuses of a user and
 * statuses of all users they are following.
 */
public class GetFeedHandler implements RequestHandler<FeedStoryRequest, FeedStoryResponse> {
    @Override
    public FeedStoryResponse handleRequest(FeedStoryRequest request, Context context) {
        FeedServiceImpl service = new FeedServiceImpl();
        return service.getFeed(request);
    }
}
