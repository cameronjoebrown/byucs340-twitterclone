package edu.byu.cs.tweeter.server.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class StoryDAO {

    public FeedStoryResponse getStory(FeedStoryRequest request) {

        assert request.getLimit() > 0;
        assert request.getUser() != null;

        List<Status> allStatuses = getDummyStory(request.getUser());
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int storyIndex = getStoryStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; storyIndex < allStatuses.size() && limitCounter < request.getLimit(); storyIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(storyIndex));
            }

            hasMorePages = storyIndex < allStatuses.size();
        }

        return new FeedStoryResponse(responseStatuses, hasMorePages);
    }

    /**
     * Determines the index for the first status in the specified 'allStatuses' list that should
     * be returned in the current request. This will be the index of the next status after the
     * specified 'lastStatus'.
     *
     * @param lastStatus the last status that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allStatuses the generated list of statuses from which we are returning paged results.
     * @return the index of the first status to be returned.
     */
    private int getStoryStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int storyIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    storyIndex = i + 1;
                    break;
                }
            }
        }

        return storyIndex;
    }

    /**
     * Returns the list of dummy story data. This is written as a separate method to allow
     * mocking of the story
     *
     * @param user the user whose story is requested so we can generate some tweets for their story
     *
     * @return the generator.
     */
    List<Status> getDummyStory(User user) {
        // Creating tweets and assigning to the specified user
        final Status status1 = new Status("Tweet.", user, LocalDateTime.of(2020, 10, 13, 15, 56));
        final Status status2 = new Status("Yello", user, LocalDateTime.of(2020, 9, 13, 15, 56));
        final Status status3 = new Status("What's up @JustinJones", user, LocalDateTime.of(2020, 8, 13, 15, 57));
        final Status status4 = new Status("a link: http://google.com", user, LocalDateTime.of(2020, 7, 13, 16, 56));
        final Status status5 = new Status("Hello", user, LocalDateTime.of(2020, 6, 6, 15, 56));
        final Status status6 = new Status("Hello @FrankFrandson", user, LocalDateTime.of(2020, 5, 13, 15, 56));
        final Status status7 = new Status("Hello Yall", user, LocalDateTime.of(2020, 4, 13, 17, 56));
        final Status status8 = new Status("Link: http://google.com", user, LocalDateTime.of(2020, 3, 13, 15, 56));
        final Status status9 = new Status("Person: @HelenHopwell Link: http://google.com", user, LocalDateTime.of(2019, 12, 15, 15, 56));
        final Status status10 = new Status("Go to this link: http://github.com", user, LocalDateTime.of(2019, 3, 13, 15, 56));
        final Status status11 = new Status("I love @IgorIsaacson", user, LocalDateTime.of(2019, 3, 13, 13, 54));
        final Status status12 = new Status("Link: http://google.com", user, LocalDateTime.of(2019, 3, 13, 11, 42));
        final Status status13 = new Status("Cool Link: http://www.google.com", user, LocalDateTime.of(2018, 7, 15, 15, 56));
        final Status status14 = new Status("Person: @GiovannaGiles Link: http://google.com", user, LocalDateTime.of(2018, 3, 13, 5, 56));

        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8,
                status9, status10, status11, status12, status13, status14);
    }

}
