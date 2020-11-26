package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;
import edu.byu.cs.tweeter.server.dao.ViewUserDAO;

public class ViewUserServiceImplTest {
    private ViewUserRequest validRequest;
    private ViewUserResponse successResponse;
    private ViewUserServiceImpl viewUserServiceImplSpy;

    /**
     * Create a ViewUserService spy that uses a mock ViewUserDAO to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        validRequest = new ViewUserRequest(user1.getUsername());

        // Setup a mock ViewUserDAO that will return known responses
        successResponse = new ViewUserResponse(user1, true);
        ViewUserDAO mockViewUserDAO = Mockito.mock(ViewUserDAO.class);
        Mockito.when(mockViewUserDAO.viewUser(validRequest)).thenReturn(successResponse);

        // Create a ViewUserService instance and wrap it with a spy that will use the mock service
        viewUserServiceImplSpy = Mockito.spy(new ViewUserServiceImpl());
        Mockito.when(viewUserServiceImplSpy.getViewUserDAO()).thenReturn(mockViewUserDAO);

    }

    /**
     * Verify that for successful requests the {@link ViewUserServiceImpl#viewUser(ViewUserRequest)}
     * method returns the same result as the {@link ViewUserDAO}.
     */
    @Test
    public void testViewUser_validRequest_correctResponse() {
        ViewUserResponse response = viewUserServiceImplSpy.viewUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

}
