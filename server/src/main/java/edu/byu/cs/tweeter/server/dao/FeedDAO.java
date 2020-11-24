package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class FeedDAO {

    // This is the hard coded followee data returned by the 'getFollowees()' and 'getFollowers()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);

    // Hard coded Mock Feed Statuses
    private final Status status1 = new Status("This is a tweet.", user1, "2018-04-11 12:56");
    private final Status status2 = new Status("This is another tweet.", user4, "2018-05-11 12:56");
    private final Status status3 = new Status("What's up @JustinJones", user1, "2018-06-11 12:56");
    private final Status status4 = new Status("Here's a link: http://google.com", user5, "2019-04-11 01:56");
    private final Status status5 = new Status("Hello Everybody", user2, "2019-08-11 12:56");
    private final Status status6 = new Status("Hello @FrankFrandson", user2, "2019-09-11 12:56");
    private final Status status7 = new Status("Hello Everybody", user2, "2019-10-11 12:56");
    private final Status status8 = new Status("Link: http://google.com", user2, "2019-11-11 12:56");
    private final Status status9 = new Status("Person: @HelenHopwell Link: http://google.com", user3, "2019-12-15 12:56");
    private final Status status10 = new Status("Go to this link: http://github.com", user19, "2020-01-15 17:56");
    private final Status status11 = new Status("I love @IgorIsaacson", user13, "2020-03-23 12:56");
    private final Status status12 = new Status("Link: http://google.com", user14, "2020-04-11 12:10");
    private final Status status13 = new Status("Cool Link: http://www.google.com", user15, "2020-04-11 21:26");
    private final Status status14 = new Status("Person: @GiovannaGiles Link: http://google.com", user16, "2020-05-18 11:56");
    private final Status status15 = new Status("This is fun", user16, "2020-10-14 12:56");

    public FeedStoryResponse getFeed(FeedStoryRequest request) {

        List<Status> allStatuses = getDummyFeed();
        Collections.sort(allStatuses);
        List<Status> responseStatuses = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int feedIndex = getFeedStartingIndex(request.getLastStatus(), allStatuses);

            for(int limitCounter = 0; feedIndex < allStatuses.size() && limitCounter < request.getLimit(); feedIndex++, limitCounter++) {
                responseStatuses.add(allStatuses.get(feedIndex));
            }

            hasMorePages = feedIndex < allStatuses.size();
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
    private int getFeedStartingIndex(String lastStatus, List<Status> allStatuses) {

        int feedIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i).getTimeStamp())) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    feedIndex = i + 1;
                    break;
                }
            }
        }

        return feedIndex;
    }

    /**
     * Returns the list of dummy feed data. This is written as a separate method to allow
     * mocking of the feed
     *
     *
     * @return the generator.
     */
    List<Status> getDummyFeed() {
        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8,
                status9, status10, status11, status12, status13, status14, status15);
    }
}
