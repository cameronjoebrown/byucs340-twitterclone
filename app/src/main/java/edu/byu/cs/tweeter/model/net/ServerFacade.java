package edu.byu.cs.tweeter.model.net;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

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

    // Hard coded Mock Feed Statuses
    private final Status status1 = new Status("This is a tweet.", user1, LocalDateTime.of(2018, 4, 11, 12, 56));
    private final Status status2 = new Status("This is another tweet.", user4, LocalDateTime.of(2018, 5, 11, 12, 56));
    private final Status status3 = new Status("What's up @JustinJones", user1, LocalDateTime.of(2018, 6, 11, 12, 56));
    private final Status status4 = new Status("Here's a link: http://google.com", user5, LocalDateTime.of(2019, 4, 11, 1, 56));
    private final Status status5 = new Status("Hello Everybody", user2, LocalDateTime.of(2019, 8, 11, 12, 56));
    private final Status status6 = new Status("Hello @FrankFrandson", user2, LocalDateTime.of(2019, 9, 11, 12, 56));
    private final Status status7 = new Status("Hello Everybody", user2, LocalDateTime.of(2019, 10, 11, 12, 56));
    private final Status status8 = new Status("Link: http://google.com", user2, LocalDateTime.of(2019, 11, 11, 12, 56));
    private final Status status9 = new Status("Person: @HelenHopwell Link: http://google.com", user3, LocalDateTime.of(2019, 12, 15, 12, 56));
    private final Status status10 = new Status("Go to this link: http://github.com", user19, LocalDateTime.of(2020, 1, 15, 17, 56));
    private final Status status11 = new Status("I love @IgorIsaacson", user13, LocalDateTime.of(2020, 3, 23, 12, 56));
    private final Status status12 = new Status("Link: http://google.com", user14, LocalDateTime.of(2020, 4, 11, 12, 10));
    private final Status status13 = new Status("Cool Link: http://www.google.com", user15, LocalDateTime.of(2020, 4, 11, 21, 26));
    private final Status status14 = new Status("Person: @GiovannaGiles Link: http://google.com", user16, LocalDateTime.of(2020, 5, 18, 11, 56));
    private final Status status15 = new Status("This is fun", user16, LocalDateTime.of(2020, 10, 14, 12, 56));

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginRegisterResponse login(LoginRequest request) {
        String username = request.getUsername();
        User user = new User("Test", "User", username,
                MALE_IMAGE_URL);
        return new LoginRegisterResponse(user, new AuthToken());
    }

    /**
     * Performs a registration and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginRegisterResponse register(RegisterRequest request) {
        String username = request.getUsername();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        User user = new User(firstName, lastName, username,
                MALE_IMAGE_URL);
        return new LoginRegisterResponse(user, new AuthToken());
    }

    /**
     *
     * @param request contains all info needed to perform a logout
     * @return the result of the logout operation
     */
    public Response logout(LogoutRequest request) {
        return new Response(true);
    }

    /**
     *
     * @param request contains all info needed to perform a follow operation
     * @return the result of the follow operation
     */
    public Response follow(FollowUnfollowRequest request) {
        return new Response(true);
    }

    /**
     *
     * @param request contains all info needed to perform an unfollow operation
     * @return the result of the unfollow operation
     */
    public Response unfollow(FollowUnfollowRequest request) {
        return new Response(true);
    }

    public ViewUserResponse viewUser(ViewUserRequest request) {
        User user = null;
        for(User u : getDummyFollows()) {
            if(u.getUsername().equals(request.getUsername())) {
                user = u;
                break;
            }
        }
        if(user == null) {
            return new ViewUserResponse("User does not exist");
        }
        return new ViewUserResponse(user, true);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        // TODO: Add status to Story. How do we allow it to be added in next refresh?
        return new PostStatusResponse(new Status(request.getStatusText(), request.getUser(), request.getTimeStamp()));
    }


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollower() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowees = getDummyFollows();
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

            for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                responseFollowees.add(allFollowees.get(followeesIndex));
            }

            hasMorePages = followeesIndex < allFollowees.size();
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFollowee the last followee that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                    break;
                }
            }
        }

        return followeesIndex;
    }

    /**
     * Returns the followers of the user specified. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the follower response.
     */
    public FollowerResponse getFollowers(FollowerRequest request) {

        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getFollowee() == null) {
                throw new AssertionError();
            }
        }

        List<User> allFollowers = getDummyFollows();
        List<User> responseFollowers = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);

            for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
                responseFollowers.add(allFollowers.get(followersIndex));
            }

            hasMorePages = followersIndex < allFollowers.size();
        }

        return new FollowerResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first follower in the specified 'allFollowers' list that should
     * be returned in the current request. This will be the index of the next follower after the
     * specified 'lastFollower'.
     *
     * @param lastFollower the last follower that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowers the generated list of followers from which we are returning paged results.
     * @return the index of the first follower to be returned.
     */
    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollower != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followersIndex = i + 1;
                }
            }
        }

        return followersIndex;
    }


    public FeedStoryResponse getStory(FeedStoryRequest request) {
        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

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

    public FeedStoryResponse getFeed(FeedStoryRequest request) {
        // Used in place of assert statements because Android does not support them
        if(BuildConfig.DEBUG) {
            if(request.getLimit() < 0) {
                throw new AssertionError();
            }

            if(request.getUser() == null) {
                throw new AssertionError();
            }
        }

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
    private int getFeedStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int feedIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
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
     * Returns the list of dummy followee/follower data. This is written as a separate method to allow
     * mocking of the followees/follower.
     *
     * @return the generator.
     */
    List<User> getDummyFollows() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
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
