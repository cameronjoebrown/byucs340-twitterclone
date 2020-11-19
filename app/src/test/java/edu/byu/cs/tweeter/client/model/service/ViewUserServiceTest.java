package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

public class ViewUserServiceTest {
    private ViewUserRequest validRequest;
    private ViewUserRequest invalidRequest;

    private ViewUserResponse successResponse;
    private ViewUserResponse failureResponse;

    private ViewUserService viewUserServiceSpy;

    /**
     * Create a ViewUserService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        validRequest = new ViewUserRequest(user1.getUsername());
        invalidRequest = new ViewUserRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new ViewUserResponse(user1, true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.viewUser(validRequest)).thenReturn(successResponse);

        failureResponse = new ViewUserResponse("An exception occured");
        Mockito.when(mockServerFacade.viewUser(invalidRequest)).thenReturn(failureResponse);

        // Create a ViewUserService instance and wrap it with a spy that will use the mock service
        viewUserServiceSpy = Mockito.spy(new ViewUserService());
        Mockito.when(viewUserServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link ViewUserService#viewUser(ViewUserRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testViewUser_validRequest_correctResponse() throws IOException {
        ViewUserResponse response = viewUserServiceSpy.viewUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link ViewUserService#viewUser(ViewUserRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testViewUser_invalidRequest_unableToLogin() throws IOException {
        ViewUserResponse response = viewUserServiceSpy.viewUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that the {@link ViewUserService#viewUser(ViewUserRequest)} method loads the
     * profile image of the user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testViewUser_validRequest_loadsProfileImages() throws IOException {
        ViewUserResponse response = viewUserServiceSpy.viewUser(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }
}
