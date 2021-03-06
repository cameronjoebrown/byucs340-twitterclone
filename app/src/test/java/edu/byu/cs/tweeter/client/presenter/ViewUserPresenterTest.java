package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.ViewUserServiceProxy;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

public class ViewUserPresenterTest {
    private ViewUserRequest request;
    private ViewUserResponse response;
    private ViewUserServiceProxy mockViewUserServiceProxy;
    private ViewUserPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User viewedUser = new User("Viewed", "User", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        request = new ViewUserRequest(viewedUser.getUsername());
        response = new ViewUserResponse(viewedUser, true);

        // Create a mock ViewUserService
        mockViewUserServiceProxy = Mockito.mock(ViewUserServiceProxy.class);
        Mockito.when(mockViewUserServiceProxy.viewUser(request)).thenReturn(response);

        // Wrap a ViewUserPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new ViewUserPresenter(new ViewUserPresenter.View() {}));
        Mockito.when(presenter.getViewUserService()).thenReturn(mockViewUserServiceProxy);
    }

    @Test
    public void testViewUser_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockViewUserServiceProxy.viewUser(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.viewUser(request));
    }

    @Test
    public void testViewUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockViewUserServiceProxy.viewUser(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.viewUser(request));
    }
}
