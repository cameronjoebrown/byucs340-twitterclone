package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;


public class FollowerIntegrationTest {
    private FollowerRequest validRequest;
    private FollowerRequest invalidRequest;

    private FollowerServiceProxy followerServiceProxy;

    /**
     * Create a FollowerService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        // Setup request objects to use in the tests
        validRequest = new FollowerRequest(currentUser.getUsername(), 3, null);
        invalidRequest = new FollowerRequest(null, 0, null);

        // Create a FollowerService instance and wrap it with a spy that will use the mock service
        followerServiceProxy = new FollowerServiceProxy();
    }

    /**
     * Verify that for successful requests the {@link FollowerServiceProxy#getFollowers(FollowerRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceProxy.getFollowers(validRequest);
        Assertions.assertNotEquals(response.getFollowers(), 0);
    }

    /**
     * Verify that the {@link FollowerServiceProxy#getFollowers(FollowerRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceProxy.getFollowers(validRequest);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FollowerServiceProxy#getFollowers(FollowerRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        FollowerResponse response = followerServiceProxy.getFollowers(invalidRequest);
        Assertions.assertEquals(response.getFollowers().size(), 0);
    }
}
