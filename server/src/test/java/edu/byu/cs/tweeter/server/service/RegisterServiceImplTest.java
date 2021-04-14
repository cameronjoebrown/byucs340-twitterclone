package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class RegisterServiceImplTest {
    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private LoginRegisterResponse successResponse;
    private LoginRegisterResponse failureResponse;

    private RegisterServiceImpl registerServiceImplSpy;

    /**
     * Create a RegisterService spy that uses a mock RegisterDAO to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        validRequest = new RegisterRequest(user1.getUsername(), "Iamcool",
                user1.getFirstName(), user1.getLastName(), user1.getImageUrl());
        invalidRequest = new RegisterRequest(null, null, null, null, null);

        // Setup a mock RegisterDAO that will return known responses
        successResponse = new LoginRegisterResponse(user1, new AuthToken("cooladfsdfdsfa"));
        UserDAO mockRegisterDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockRegisterDAO.register(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginRegisterResponse("An exception occurred");
        Mockito.when(mockRegisterDAO.register(invalidRequest)).thenReturn(failureResponse);

        // Create a RegisterService instance and wrap it with a spy that will use the mock service
        registerServiceImplSpy = Mockito.spy(new RegisterServiceImpl());
        Mockito.when(registerServiceImplSpy.getUserDAO()).thenReturn(mockRegisterDAO);

    }

    /**
     * Verify that for successful requests the {@link RegisterServiceImpl#register(RegisterRequest)}
     * method returns the same result as the {@link UserDAO}.

     */
    @Test
    public void testRegister_validRequest() {
        LoginRegisterResponse response = registerServiceImplSpy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link RegisterServiceImpl#register(RegisterRequest)}
     * method returns the same result as the {@link UserDAO}.
     */
    @Test
    public void testRegister_invalidRequest() {
        LoginRegisterResponse response = registerServiceImplSpy.register(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

}
