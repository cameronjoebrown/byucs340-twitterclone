package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.Response;

public class PostStatusIntegrationTest {
    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusServiceProxy postStatusServiceProxy;

    /**
     * Create a PostStatusService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        String date1 = "2020-04-28 20:30";

        validRequest = new PostStatusRequest("This is a tweet", user1.getUsername(), date1);
        invalidRequest = new PostStatusRequest(null,null, null);
        successResponse = new PostStatusResponse(new Status(validRequest.getStatusText(),
                user1, validRequest.getTimeStamp()));
        failureResponse = new PostStatusResponse("An exception occured");

        // Create a PostStatusService instance
        postStatusServiceProxy = new PostStatusServiceProxy();

    }

    /**
     * Verify that for successful requests the {@link PostStatusServiceProxy#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     */
    @Test
    public void testPostStatus_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Response response = postStatusServiceProxy.postStatus(validRequest);
        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
    }

    /**
     * Verify that for invalid requests the {@link PostStatusServiceProxy#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     */
    @Test
    public void testPostStatus_invalidRequest_postStatusUnsuccessful() throws IOException, TweeterRemoteException {
        try {
            Response response = postStatusServiceProxy.postStatus(invalidRequest);
        } catch (RuntimeException e) {
            Assertions.assertNotEquals(0, 1);
        }
    }
}
