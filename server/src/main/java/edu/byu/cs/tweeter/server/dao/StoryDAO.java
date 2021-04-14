package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class StoryDAO {

    // This is the hard coded followee data returned by the 'getFollowees()' and 'getFollowers()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);

    public FeedStoryResponse getStory(FeedStoryRequest request) {

        assert request.getLimit() > 0;
        assert request.getUsername() != null;

        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        for(User u : getDummyFollows()) {
            if(u.getUsername().equals(request.getUsername())) {
                user = u;
                break;
            }
        }

        List<Status> allStatuses = getDummyStory(user);
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
    private int getStoryStartingIndex(String lastStatus, List<Status> allStatuses) {

        int storyIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i).getTimeStamp())) {
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
        final Status status1 = new Status("Tweet.", user, "2020-10-13 15:56");
        final Status status2 = new Status("Yello", user, "2020-09-13 15:56");
        final Status status3 = new Status("What's up @JustinJones", user, "2020-08-13 15:57");
        final Status status4 = new Status("a link: http://google.com", user, "2020-07-13 16:56");
        final Status status5 = new Status("Hello", user, "2020-06-06 15:56");
        final Status status6 = new Status("Hello @FrankFrandson", user, "2020-05-13 15:56");
        final Status status7 = new Status("Hello Yall", user, "2020-04-13 17:56");
        final Status status8 = new Status("Link: http://google.com", user, "2020-03-13 15:56");
        final Status status9 = new Status("Person: @HelenHopwell Link: http://google.com", user, "2019-12-15 15:56");
        final Status status10 = new Status("Go to this link: http://github.com", user, "2019-03-13 15:56");
        final Status status11 = new Status("I love @IgorIsaacson", user, "2019-03-13 13:54");
        final Status status12 = new Status("Link: http://google.com", user,"2019-03-13 11:42");
        final Status status13 = new Status("Cool Link: http://www.google.com", user, "2018-07-15 15:56");
        final Status status14 = new Status("Person: @GiovannaGiles Link: http://google.com", user, "2018-03-13 05:56");

        return Arrays.asList(status1, status2, status3, status4, status5, status6, status7, status8,
                status9, status10, status11, status12, status13, status14);
    }

    List<User> getDummyFollows() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new PostStatusResponse(new Status(request.getStatusText(), user, request.getTimeStamp()));
    }

}
