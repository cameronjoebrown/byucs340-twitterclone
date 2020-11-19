package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class LoginServiceTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginRegisterResponse successResponse;
    private LoginRegisterResponse failureResponse;

    private LoginService loginServiceSpy;

    /**
     * Create a LoginService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        validRequest = new LoginRequest(user1.getUsername(), "Iamcool");
        invalidRequest = new LoginRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginRegisterResponse(user1, new AuthToken());
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.login(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginRegisterResponse("An exception occured");
        Mockito.when(mockServerFacade.login(invalidRequest)).thenReturn(failureResponse);

        // Create a LoginService instance and wrap it with a spy that will use the mock service
        loginServiceSpy = Mockito.spy(new LoginService());
        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link LoginService#login(LoginRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogin_validRequest() throws IOException {
        LoginRegisterResponse response = loginServiceSpy.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link LoginService#login(LoginRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogin_invalidRequest_unableToLogin() throws IOException {
        LoginRegisterResponse response = loginServiceSpy.login(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that the {@link LoginService#login(LoginRequest)} method loads the
     * profile image of the user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogin_validRequest_loadsProfileImages() throws IOException {
        LoginRegisterResponse response = loginServiceSpy.login(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }
}
