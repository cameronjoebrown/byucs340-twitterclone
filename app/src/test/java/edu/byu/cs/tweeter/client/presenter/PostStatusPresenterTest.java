package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.PostStatusServiceProxy;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;


public class PostStatusPresenterTest {
    private PostStatusRequest request;
    private PostStatusResponse response;
    private PostStatusServiceProxy mockPostStatusServiceProxy;
    private PostStatusPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        String date1 = "2020-04-28 20:30";
        Status status1 = new Status("This is a status", currentUser, date1);

        request = new PostStatusRequest(status1.getStatusText(), currentUser.getUsername(), date1);
        response = new PostStatusResponse(status1);

        // Create a mock FollowingService
        mockPostStatusServiceProxy = Mockito.mock(PostStatusServiceProxy.class);
        Mockito.when(mockPostStatusServiceProxy.postStatus(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new PostStatusPresenter(new PostStatusPresenter.View() {}));
        Mockito.when(presenter.getPostStatusService()).thenReturn(mockPostStatusServiceProxy);
    }

    @Test
    public void testPostStatus_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockPostStatusServiceProxy.postStatus(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.postStatus(request));
    }
}
