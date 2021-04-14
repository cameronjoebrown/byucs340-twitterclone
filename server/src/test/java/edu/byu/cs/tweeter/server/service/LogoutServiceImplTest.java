package edu.byu.cs.tweeter.server.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;

public class LogoutServiceImplTest {
    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private LogoutServiceImpl LogoutServiceImplSpy;

    /**
     * Create a LogoutService spy that uses a mock LogoutDAO to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(new AuthToken("dd"));
        invalidRequest = new LogoutRequest(null);

        // Setup a mock LogoutDAO that will return known responses
        successResponse = new Response(true);
        AuthTokenDAO mockLogoutDAO = Mockito.mock(AuthTokenDAO.class);
        Mockito.when((mockLogoutDAO.logout(validRequest))).thenReturn(successResponse);

        failureResponse = new Response(false, "error occurred");
        Mockito.when(mockLogoutDAO.logout(invalidRequest)).thenReturn(failureResponse);

        // Create a LogoutService instance and wrap it with a spy that will use the mock service
        LogoutServiceImplSpy = Mockito.spy(new LogoutServiceImpl());
        Mockito.when(LogoutServiceImplSpy.getAuthTokenDAO()).thenReturn(mockLogoutDAO);

    }

    /**
     * Verify that for successful requests the {@link LogoutServiceImpl#logout(LogoutRequest)}
     * method returns the same result as the {@link AuthTokenDAO}.
     */
    @Test
    public void testLogout_validRequest() {
        Response response = LogoutServiceImplSpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link LogoutServiceImpl#logout(LogoutRequest)}
     * method returns the same result as the {@link AuthTokenDAO}.
     */
    @Test
    public void testLogout_invalidRequest() {
        Response response = LogoutServiceImplSpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
