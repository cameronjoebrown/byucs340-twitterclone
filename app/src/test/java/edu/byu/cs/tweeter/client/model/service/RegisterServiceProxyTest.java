package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class RegisterServiceProxyTest {
    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private LoginRegisterResponse successResponse;
    private LoginRegisterResponse failureResponse;

    private RegisterServiceProxy registerServiceProxySpy;

    /**
     * Create a RegisterService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        validRequest = new RegisterRequest(user1.getUsername(), "Iamcool",
                user1.getFirstName(), user1.getLastName(), user1.getImageUrl());
        invalidRequest = new RegisterRequest(null, null, null, null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginRegisterResponse(user1, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest, "/register")).thenReturn(successResponse);

        failureResponse = new LoginRegisterResponse("An exception occured");
        Mockito.when(mockServerFacade.register(invalidRequest, "")).thenReturn(failureResponse);

        // Create a RegisterService instance and wrap it with a spy that will use the mock service
        registerServiceProxySpy = Mockito.spy(new RegisterServiceProxy());
        Mockito.when(registerServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link RegisterServiceProxy#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_validRequest() throws IOException, TweeterRemoteException {
        LoginRegisterResponse response = registerServiceProxySpy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link RegisterServiceProxy#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_invalidRequest() throws IOException, TweeterRemoteException {
        LoginRegisterResponse response = registerServiceProxySpy.register(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that the {@link RegisterServiceProxy#register(RegisterRequest)} method loads the
     * profile image of the user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        LoginRegisterResponse response = registerServiceProxySpy.register(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }
}
