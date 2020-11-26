package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.dao.UnfollowDAO;

public class UnfollowServiceImplTest {
    private FollowUnfollowRequest validRequest;
    private FollowUnfollowRequest invalidRequest;

    private Response successResponse;
    private Response failureResponse;

    private UnfollowServiceImpl unfollowServiceImplSpy;

    /**
     * Create a UnfollowService spy that uses a mock UnfollowDAO to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user2 = new User("Rob", "Lowe", "@roblowe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        validRequest = new FollowUnfollowRequest(user1.getUsername(), user2.getUsername());
        invalidRequest = new FollowUnfollowRequest(null, null);

        // Setup a mock UnfollowDAO that will return known responses
        successResponse = new Response(true);
        UnfollowDAO mockUnfollowDAO = Mockito.mock(UnfollowDAO.class);
        Mockito.when(mockUnfollowDAO.unfollow(validRequest)).thenReturn(successResponse);

        failureResponse = new Response(false, "An exception occured");
        Mockito.when(mockUnfollowDAO.unfollow(invalidRequest)).thenReturn(failureResponse);

        // Create a UnfollowService instance and wrap it with a spy that will use the mock service
        unfollowServiceImplSpy = Mockito.spy(new UnfollowServiceImpl());
        Mockito.when(unfollowServiceImplSpy.getUnfollowDAO()).thenReturn(mockUnfollowDAO);

    }

    /**
     * Verify that for successful requests the {@link UnfollowServiceImpl#unfollow(FollowUnfollowRequest)}
     * method returns the same result as the {@link UnfollowDAO}.
     * .
     */
    @Test
    public void testUnfollow_validRequest_correctResponse() {
        Response response = unfollowServiceImplSpy.unfollow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link UnfollowServiceImpl#unfollow(FollowUnfollowRequest)}
     * method returns the same result as the {@link UnfollowDAO}.
     *
     */
    @Test
    public void testUnfollow_invalidRequest_unFollowUnsuccessful() {
        Response response = unfollowServiceImplSpy.unfollow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
