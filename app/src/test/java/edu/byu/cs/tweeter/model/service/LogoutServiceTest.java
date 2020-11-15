package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutServiceTest {
    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private LogoutService logoutServiceSpy;

    /**
     * Create a LogoutService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(new AuthToken());
        invalidRequest = new LogoutRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new Response(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when((mockServerFacade.logout(validRequest))).thenReturn(successResponse);

        failureResponse = new Response(false, "error occurred");
        Mockito.when(mockServerFacade.logout(invalidRequest)).thenReturn(failureResponse);

        // Create a LogoutService instance and wrap it with a spy that will use the mock service
        logoutServiceSpy = Mockito.spy(new LogoutService());
        Mockito.when(logoutServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link LogoutService#logout(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_validRequest() throws IOException {
        Response response = logoutServiceSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link LogoutService#logout(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_invalidRequest() throws IOException {
        Response response = logoutServiceSpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
