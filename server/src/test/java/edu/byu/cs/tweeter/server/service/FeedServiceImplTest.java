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
import edu.byu.cs.tweeter.server.dao.FeedDAO;

public class FeedServiceImplTest {
    private FeedStoryRequest request;
    private FeedStoryResponse expectedResponse;
    private FeedServiceImpl feedServiceImplSpy;

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

        String date1 = "2020-04-28 20:30";
        String date2 = "2020-06-28 20:30";
        String date3 = "2020-04-28 5:30";

        Status resultStatus1 = new Status("I am the coolest in the world", resultUser1, date1);
        Status resultStatus2 = new Status("Hanging out with @coolbob", resultUser2, date3);
        Status resultStatus3 = new Status("Never mind, I'm no longer the coolest in the world.",
                resultUser3, date2);
        // Setup request object to use in the tests
        request = new FeedStoryRequest(currentUser.getUsername(), 3, null);

        // Setup a mock ServerFacade that will return known responses
        expectedResponse = new FeedStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        FeedDAO mockFeedDAO = Mockito.mock(FeedDAO.class);
        Mockito.when(mockFeedDAO.getFeed(request)).thenReturn(expectedResponse);

        // Create a FeedStoryService instance and wrap it with a spy that will use the mock service
        feedServiceImplSpy = Mockito.spy(new FeedServiceImpl());
        Mockito.when(feedServiceImplSpy.getFeedDAO()).thenReturn(mockFeedDAO);
    }

    /**
     * Verify that for successful requests the {@link FeedServiceImpl#getFeed(FeedStoryRequest)}
     * method returns the same result as the {@link FeedDAO}.
     */
    @Test
    public void testGetFeed_validRequest_correctResponse() {
        FeedStoryResponse response = feedServiceImplSpy.getFeed(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
