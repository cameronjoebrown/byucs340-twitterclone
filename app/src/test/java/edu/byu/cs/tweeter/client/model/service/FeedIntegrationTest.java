package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class FeedIntegrationTest {
    private FeedStoryRequest validRequest;
    private FeedStoryRequest invalidRequest;
    private FeedServiceProxy feedServiceProxy;

    /**
     * Create a FeedService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        // Setup request objects to use in the tests
        validRequest = new FeedStoryRequest(currentUser.getUsername(), 3, null);
        invalidRequest = new FeedStoryRequest(null, 0, null);
        feedServiceProxy = new FeedServiceProxy();
    }

    /**
     * Verify that for successful requests the {@link FeedServiceProxy#getFeed(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedStoryResponse response = feedServiceProxy.getFeed(validRequest);
        Assertions.assertNotEquals(response.getStatuses().size(), 0);
    }

    /**
     * Verify that the {@link FeedServiceProxy#getFeed(FeedStoryRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FeedStoryResponse response = feedServiceProxy.getFeed(validRequest);

        for(Status status : response.getStatuses()) {
            Assertions.assertNotNull(status.getUser().getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FeedServiceProxy#getFeed(FeedStoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_invalidRequest_returnsNoFeed() throws IOException, TweeterRemoteException {
        FeedStoryResponse response = feedServiceProxy.getFeed(invalidRequest);
        Assertions.assertEquals(response.getStatuses().size(), 0);
    }
}
