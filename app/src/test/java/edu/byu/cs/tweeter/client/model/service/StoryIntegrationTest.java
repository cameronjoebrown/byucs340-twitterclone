package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class StoryIntegrationTest {
    private FeedStoryRequest validRequest;
    private FeedStoryRequest invalidRequest;

    private FeedStoryResponse successResponse;
    private FeedStoryResponse failureResponse;

    private StoryServiceProxy storyServiceProxy;

    /**
     * Create a FeedService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup test user to
        User currentUser = new User("FirstName", "LastName", "@CoolUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        String date1 = "2020-04-28 20:30";
        String date2 = "2020-06-28 20:30";
        String date3 = "2020-04-28 5:30";

        Status resultStatus1 = new Status("I am the coolest in the world", currentUser, date1);
        Status resultStatus2 = new Status("Hanging out with @coolbob", currentUser, date3);
        Status resultStatus3 = new Status("Nevermind, I'm no longer the coolest in the world.",
                                            currentUser, date2);

        // Setup request objects to use in the tests
        validRequest = new FeedStoryRequest(currentUser.getUsername(), 3, null);
        invalidRequest = new FeedStoryRequest(null, 0, null);

        successResponse = new FeedStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        failureResponse = new FeedStoryResponse("An exception occured");

        // Create a FeedStoryService instance
        storyServiceProxy = new StoryServiceProxy();
    }

    /**
     * Verify that for successful requests the {@link StoryServiceProxy#getStory(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedStoryResponse response = storyServiceProxy.getStory(validRequest);
        Assertions.assertNotEquals(response.getStatuses().size(), 0);
    }

    /**
     * Verify that the {@link StoryServiceProxy#getStory(FeedStoryRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FeedStoryResponse response = storyServiceProxy.getStory(validRequest);

        for(Status status : response.getStatuses()) {
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link StoryServiceProxy#getStory(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_invalidRequest_returnsNoStory() throws IOException, TweeterRemoteException {
        FeedStoryResponse response = storyServiceProxy.getStory(invalidRequest);
        Assertions.assertEquals(response.getStatuses().size(), 0);
    }
}
