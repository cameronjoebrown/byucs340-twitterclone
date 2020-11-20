package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutServiceProxyTest {
    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private LogoutServiceProxy logoutServiceProxySpy;

    /**
     * Create a LogoutService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(new AuthToken());
        invalidRequest = new LogoutRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new Response(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when((mockServerFacade.logout(validRequest, "/logout"))).thenReturn(successResponse);

        failureResponse = new Response(false, "error occurred");
        Mockito.when(mockServerFacade.logout(invalidRequest, "")).thenReturn(failureResponse);

        // Create a LogoutService instance and wrap it with a spy that will use the mock service
        logoutServiceProxySpy = Mockito.spy(new LogoutServiceProxy());
        Mockito.when(logoutServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link LogoutServiceProxy#logout(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_validRequest() throws IOException, TweeterRemoteException {
        Response response = logoutServiceProxySpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link LogoutServiceProxy#logout(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_invalidRequest() throws IOException, TweeterRemoteException {
        Response response = logoutServiceProxySpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
