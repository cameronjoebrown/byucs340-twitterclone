package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class FollowingIntegrationTest {

    private FollowingRequest validRequest;
    private FollowingRequest invalidRequest;

    private FollowingServiceProxy followingServiceProxy;

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);


        // Setup request objects to use in the tests
        validRequest = new FollowingRequest(currentUser.getUsername(), 3, null);
        invalidRequest = new FollowingRequest(null, 0, null);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        followingServiceProxy = new FollowingServiceProxy();
    }

    /**
     * Verify that for successful requests the {@link FollowingServiceProxy#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceProxy.getFollowees(validRequest);
        Assertions.assertNotEquals(response.getFollowees().size(), 0);
    }

    /**
     * Verify that the {@link FollowingServiceProxy#getFollowees(FollowingRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceProxy.getFollowees(validRequest);

        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FollowingServiceProxy#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_invalidRequest_returnsNoFollowees() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceProxy.getFollowees(invalidRequest);
        Assertions.assertEquals(0, response.getFollowees().size());
    }
}