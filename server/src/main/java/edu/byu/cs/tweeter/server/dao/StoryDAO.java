package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryDAO {
    /**
     * This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    private FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

    public StoryResponse getStory(StoryRequest request){
        boolean hasMorePages = false;
        List<Status> allStatuses = getFollowGenerator().getStory(request.getUser());
        if(allStatuses == null) {
            getFollowGenerator().generateStatuses();
        }
        List<Status> responseStatuses = new ArrayList<>();
        if(request.getLimit() > 0) {
            int statusIndex = getStoryStartingIndex(request.getLastStatus(), allStatuses);

            assert allStatuses != null;
            for (int limitCounter = 0; statusIndex < allStatuses.size() &&
                    limitCounter < request.getLimit(); statusIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(statusIndex));
            }

            hasMorePages = statusIndex < allStatuses.size();
        }
        return new StoryResponse(responseStatuses, hasMorePages);
    }

    private int getStoryStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int followersIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                }
            }
        }

        return followersIndex;
    }
}
