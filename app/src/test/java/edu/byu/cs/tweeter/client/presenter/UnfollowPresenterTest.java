package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.client.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class UnfollowPresenterTest {
    private FollowUnfollowRequest request;
    private Response response;
    private UnfollowService mockFollowService;
    private UnfollowPresenter presenter;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User followedUser = new User("Rob", "Lowe", "@roblowe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");


        request = new FollowUnfollowRequest(followedUser, currentUser);
        response = new Response(true);

        // Create a mock UnfollowService
        mockFollowService = Mockito.mock(UnfollowService.class);
        Mockito.when(mockFollowService.unfollow(request)).thenReturn(response);

        // Wrap a UnfollowPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new UnfollowPresenter(new UnfollowPresenter.View() {}));
        Mockito.when(presenter.getUnfollowService()).thenReturn(mockFollowService);
    }

    @Test
    public void testUnfollow_returnsServiceResult() {
        Mockito.when(mockFollowService.unfollow(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.unfollow(request));
    }
}
