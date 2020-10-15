package edu.byu.cs.tweeter.model.service;

public class FeedServiceTest {
    /*
    private FeedStoryRequest validRequest;
    private FeedStoryRequest invalidRequest;

    private FeedStoryResponse successResponse;
    private FeedStoryResponse failureResponse;

    private FeedService feedServiceSpy;

    /**
     * Create a FeedService spy that uses a mock ServerFacade to return known responses to
     * requests.

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup request objects to use in the tests
        validRequest = new FeedStoryRequest(currentUser, 3, null);
        invalidRequest = new FeedStoryRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FeedStoryResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
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

    @Test
    public void testGetFeed_invalidRequest_returnsNoFeed() throws IOException {
        FeedStoryResponse response = feedServiceSpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
    */
}
