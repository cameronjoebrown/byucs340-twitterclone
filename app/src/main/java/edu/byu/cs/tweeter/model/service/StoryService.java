package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * Contains the business logic for getting the statuses of a user
 */
public class StoryService extends UserService {
    FeedStoryRequest request;
    FeedStoryResponse response;
    /**
     * Returns the statuses that the user specified in the request has posted. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. Uses the {@link ServerFacade} to
     * get the statuses from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    public FeedStoryResponse getStory(FeedStoryRequest request) throws IOException {
        this.request = request;
        runService();
        return response;
    }


    @Override
    boolean runMethod(ServerFacade serverFacade) {
        this.response = serverFacade.getStory(request);
        return response.isSuccess();
    }

    @Override
    void load() throws IOException {
        for(Status status : response.getStatuses()) {
            loadImage(status.getUser());
        }
    }
}
