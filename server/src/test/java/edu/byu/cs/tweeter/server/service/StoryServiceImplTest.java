package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class StoryServiceImplTest {
    private FeedStoryRequest validRequest;
    private FeedStoryRequest invalidRequest;

    private FeedStoryResponse successResponse;
    private FeedStoryResponse failureResponse;

    private StoryServiceImpl storyServiceImplSpy;

    /**
     * Create a FeedService spy that uses a mock StoryDAO to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup test user to
        User currentUser = new User("FirstName", "LastName", "@CoolUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        String date1 = "2020-04-28 20:30";
        String date2 = "2020-06-28 20:30";
        String date3 = "2020-04-28 5:30";

        Status resultStatus1 = new Status("I am the coolest in the world", currentUser, date1);
        Status resultStatus2 = new Status("Hanging out with @coolbob", currentUser, date3);
        Status resultStatus3 = new Status("Never mind, I'm no longer the coolest in the world.",
                currentUser, date2);

        // Setup request objects to use in the tests
        validRequest = new FeedStoryRequest(currentUser.getUsername(), 3, null);
        invalidRequest = new FeedStoryRequest(null, 0, null);

        // Setup a mock StoryDAO that will return known responses
        successResponse = new FeedStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        StoryDAO mockStoryDAO = Mockito.mock(StoryDAO.class);
        Mockito.when(mockStoryDAO.getStory(validRequest)).thenReturn(successResponse);

        failureResponse = new FeedStoryResponse("An exception occurred");
        Mockito.when(mockStoryDAO.getStory(invalidRequest)).thenReturn(failureResponse);

        // Create a FeedStoryService instance and wrap it with a spy that will use the mock service
        storyServiceImplSpy = Mockito.spy(new StoryServiceImpl());
        Mockito.when(storyServiceImplSpy.getStoryDAO()).thenReturn(mockStoryDAO);
    }

    /**
     * Verify that for successful requests the {@link StoryServiceImpl#getStory(FeedStoryRequest)}
     * method returns the same result as the {@link StoryDAO}.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() {
        FeedStoryResponse response = storyServiceImplSpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link StoryServiceImpl#getStory(FeedStoryRequest)}
     * method returns the same result as the {@link StoryDAO}.
     */
    @Test
    public void testGetStory_invalidRequest_returnsNoStory() {
        FeedStoryResponse response = storyServiceImplSpy.getStory(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
        Assertions.assertNotNull(response.getMessage());
    }
}
