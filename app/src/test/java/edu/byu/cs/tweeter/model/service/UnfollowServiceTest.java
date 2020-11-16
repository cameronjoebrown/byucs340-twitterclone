package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class UnfollowServiceTest {
    private FollowUnfollowRequest validRequest;
    private FollowUnfollowRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private UnfollowService unfollowServiceSpy;

    /**
     * Create a UnfollowService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user2 = new User("Rob", "Lowe", "@roblowe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        validRequest = new FollowUnfollowRequest(user1, user2);
        invalidRequest = new FollowUnfollowRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new Response(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.unfollow(validRequest)).thenReturn(successResponse);

        failureResponse = new Response(false, "An exception occured");
        Mockito.when(mockServerFacade.unfollow(invalidRequest)).thenReturn(failureResponse);

        // Create a UnfollowService instance and wrap it with a spy that will use the mock service
        unfollowServiceSpy = Mockito.spy(new UnfollowService());
        Mockito.when(unfollowServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link UnfollowService#unfollow(FollowUnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     */
    @Test
    public void testUnfollow_validRequest_correctResponse() {
        Response response = unfollowServiceSpy.unfollow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link UnfollowService#unfollow(FollowUnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     */
    @Test
    public void testUnfollow_invalidRequest_unFollowUnsuccessful() {
        Response response = unfollowServiceSpy.unfollow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
