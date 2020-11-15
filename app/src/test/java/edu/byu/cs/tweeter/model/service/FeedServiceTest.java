package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class FeedServiceTest {
    private FeedStoryRequest validRequest;
    private FeedStoryRequest invalidRequest;

    private FeedStoryResponse successResponse;
    private FeedStoryResponse failureResponse;

    private FeedService feedServiceSpy;

    /**
     * Create a FeedService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        LocalDateTime date1 = LocalDateTime.of(2020, Month.APRIL, 28, 20, 30);
        LocalDateTime date2 = LocalDateTime.of(2020, Month.JUNE, 28, 20, 30);
        LocalDateTime date3 = LocalDateTime.of(2020, Month.APRIL, 28, 5, 30);

        Status resultStatus1 = new Status("I am the coolest in the world", resultUser1, date1);
        Status resultStatus2 = new Status("Hanging out with @coolbob", resultUser2, date3);
        Status resultStatus3 = new Status("Nevermind, I'm no longer the coolest in the world.",
                resultUser3, date2);
        // Setup request objects to use in the tests
        validRequest = new FeedStoryRequest(currentUser, 3, null);
        invalidRequest = new FeedStoryRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FeedStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest)).thenReturn(successResponse);

        failureResponse = new FeedStoryResponse("An exception occured");
        Mockito.when(mockServerFacade.getFeed(invalidRequest)).thenReturn(failureResponse);

        // Create a FeedStoryService instance and wrap it with a spy that will use the mock service
        feedServiceSpy = Mockito.spy(new FeedService());
        Mockito.when(feedServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FeedService#getFeed(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException {
        FeedStoryResponse response = feedServiceSpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link FeedService#getFeed(FeedStoryRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_validRequest_loadsProfileImages() throws IOException {
        FeedStoryResponse response = feedServiceSpy.getFeed(validRequest);

        for(Status status : response.getStatuses()) {
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FeedService#getFeed(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_invalidRequest_returnsNoFeed() throws IOException {
        FeedStoryResponse response = feedServiceSpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
