package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class FollowPresenterTest {
    private NumFollowsRequest request;
    private Response response;
    private FollowServiceProxy mockFollowServiceProxy;
    private FollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User followedUser = new User("Rob", "Lowe", "@roblowe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");


        request = new NumFollowsRequest(followedUser.getUsername(), currentUser.getUsername());
        response = new Response(true);

        // Create a mock FollowService
        mockFollowServiceProxy = Mockito.mock(FollowServiceProxy.class);
        Mockito.when(mockFollowServiceProxy.follow(request)).thenReturn(response);

        // Wrap a FollowPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FollowPresenter(new FollowPresenter.View() {}));
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowServiceProxy);
    }

    @Test
    public void testFollow_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowServiceProxy.follow(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.follow(request));
    }

}
