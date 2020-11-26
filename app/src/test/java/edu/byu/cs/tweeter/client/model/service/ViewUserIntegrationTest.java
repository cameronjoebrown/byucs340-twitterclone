package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

public class ViewUserIntegrationTest {
    private ViewUserRequest validRequest;
    private ViewUserResponse successResponse;
    private ViewUserServiceProxy viewUserServiceProxy;

    /**
     * Create a ViewUserService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Bobson", "@BobBobson",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        validRequest = new ViewUserRequest(user1.getUsername());

        // Setup a mock ServerFacade that will return known responses
        successResponse = new ViewUserResponse(user1, true);

        // Create a ViewUserService instance and wrap it with a spy that will use the mock service
        viewUserServiceProxy = new ViewUserServiceProxy();

    }

    /**
     * Verify that for successful requests the {@link ViewUserServiceProxy#viewUser(ViewUserRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testViewUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        ViewUserResponse response = viewUserServiceProxy.viewUser(validRequest);
        Assertions.assertEquals(successResponse.getUser(), response.getUser());
    }

    /**
     * Verify that the {@link ViewUserServiceProxy#viewUser(ViewUserRequest)} method loads the
     * profile image of the user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testViewUser_validRequest_loadsProfileImage() throws IOException, TweeterRemoteException {
        ViewUserResponse response = viewUserServiceProxy.viewUser(validRequest);
        Assertions.assertNotNull(response.getUser().getImageBytes());
    }
}
