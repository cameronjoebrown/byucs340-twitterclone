package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class LoginServiceImplTest {
    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginRegisterResponse successResponse;
    private LoginRegisterResponse failureResponse;

    private LoginServiceImpl loginServiceProxySpy;

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
        successResponse = new LoginRegisterResponse(user1, new AuthToken("token123123123"));
        UserDAO mockLoginDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockLoginDAO.login(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginRegisterResponse("An exception occurred");
        Mockito.when(mockLoginDAO.login(invalidRequest)).thenReturn(failureResponse);

        // Create a LoginService instance and wrap it with a spy that will use the mock service
        loginServiceProxySpy = Mockito.spy(new LoginServiceImpl());
        Mockito.when(loginServiceProxySpy.getUserDAO()).thenReturn(mockLoginDAO);

    }

    /**
     * Verify that for successful requests the {@link LoginServiceImpl#login(LoginRequest)}
     * method returns the same result as the {@link UserDAO}.
     */
    @Test
    public void testLogin_validRequest() {
        LoginRegisterResponse response = loginServiceProxySpy.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link LoginServiceImpl#login(LoginRequest)}
     * method returns the same result as the {@link UserDAO}.
     */
    @Test
    public void testLogin_invalidRequest_unableToLogin() {
        LoginRegisterResponse response = loginServiceProxySpy.login(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
