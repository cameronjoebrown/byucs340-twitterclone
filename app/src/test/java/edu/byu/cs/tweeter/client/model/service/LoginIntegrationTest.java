package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class LoginIntegrationTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginRegisterResponse successResponse;
    private LoginRegisterResponse failureResponse;

    private LoginServiceProxy loginServiceProxy;

    /**
     * Create a LoginService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        User user1 = new User("Test", "User", "@TestUser",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        validRequest = new LoginRequest(user1.getUsername(), "Iamcool");
        invalidRequest = new LoginRequest(null, null);

        successResponse = new LoginRegisterResponse(user1, new AuthToken("token123123123"));
        failureResponse = new LoginRegisterResponse("An exception occured");
        loginServiceProxy = new LoginServiceProxy();

    }

    /**
     * Verify that for successful requests the {@link LoginServiceProxy#login(LoginRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogin_validRequest() throws IOException, TweeterRemoteException {
        LoginRegisterResponse response = loginServiceProxy.login(validRequest);
        Assertions.assertEquals(successResponse.getUser(), response.getUser());
    }

    /**
     * Verify that for invalid requests the {@link LoginServiceProxy#login(LoginRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogin_invalidRequest_unableToLogin() throws IOException, TweeterRemoteException {
        LoginRegisterResponse response = loginServiceProxy.login(invalidRequest);
        Assertions.assertEquals(!failureResponse.isSuccess(), response.isSuccess());
    }

    /**
     * Verify that the {@link LoginServiceProxy#login(LoginRequest)} method loads the
     * profile image of the user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogin_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        LoginRegisterResponse response = loginServiceProxy.login(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }
}
