package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class StoryServiceTest {
    private FeedStoryRequest validRequest;
    private FeedStoryRequest invalidRequest;

    private FeedStoryResponse successResponse;
    private FeedStoryResponse failureResponse;

    private StoryService storyServiceSpy;

    /**
     * Create a FeedService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup test user to
        User currentUser = new User("FirstName", "LastName", "@CoolUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        LocalDateTime date1 = LocalDateTime.of(2020, Month.APRIL, 28, 20, 30);
        LocalDateTime date2 = LocalDateTime.of(2020, Month.JUNE, 28, 20, 30);
        LocalDateTime date3 = LocalDateTime.of(2020, Month.APRIL, 28, 5, 30);


        Status resultStatus1 = new Status("I am the coolest in the world", currentUser, date1);
        Status resultStatus2 = new Status("Hanging out with @coolbob", currentUser, date3);
        Status resultStatus3 = new Status("Nevermind, I'm no longer the coolest in the world.",
                                            currentUser, date2);

        // Setup request objects to use in the tests
        validRequest = new FeedStoryRequest(currentUser, 3, null);
        invalidRequest = new FeedStoryRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FeedStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(validRequest)).thenReturn(successResponse);

        failureResponse = new FeedStoryResponse("An exception occured");
        Mockito.when(mockServerFacade.getStory(invalidRequest)).thenReturn(failureResponse);

        // Create a FeedStoryService instance and wrap it with a spy that will use the mock service
        storyServiceSpy = Mockito.spy(new StoryService());
        Mockito.when(storyServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link StoryService#getStory(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException {
        FeedStoryResponse response = storyServiceSpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link StoryService#getStory(FeedStoryRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_validRequest_loadsProfileImages() throws IOException {
        FeedStoryResponse response = storyServiceSpy.getStory(validRequest);

        for(Status status : response.getStatuses()) {
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link StoryService#getStory(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_invalidRequest_returnsNoStory() throws IOException {
        FeedStoryResponse response = storyServiceSpy.getStory(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
        Assertions.assertNotNull(response.getMessage());
    }
}
