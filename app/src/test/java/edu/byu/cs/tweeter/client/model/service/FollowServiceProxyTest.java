package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class FollowServiceProxyTest {
    private FollowUnfollowRequest validRequest;
    private FollowUnfollowRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private FollowServiceProxy followServiceProxySpy;

    /**
     * Create a FollowService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user2 = new User("Rob", "Lowe", "@roblowe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        validRequest = new FollowUnfollowRequest(user1.getUsername(), user2.getUsername());
        invalidRequest = new FollowUnfollowRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new Response(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.follow(validRequest, "/follow")).thenReturn(successResponse);

        failureResponse = new Response(false, "An exception occured");
        Mockito.when(mockServerFacade.follow(invalidRequest, "/follow")).thenReturn(failureResponse);

        // Create a FollowService instance and wrap it with a spy that will use the mock service
        followServiceProxySpy = Mockito.spy(new FollowServiceProxy());
        Mockito.when(followServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link FollowServiceProxy#follow(FollowUnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     */
    @Test
    public void testUnfollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Response response = followServiceProxySpy.follow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link FollowServiceProxy#follow(FollowUnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     */
    @Test
    public void testUnfollow_invalidRequest() throws IOException, TweeterRemoteException {
        Response response = followServiceProxySpy.follow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }


}
