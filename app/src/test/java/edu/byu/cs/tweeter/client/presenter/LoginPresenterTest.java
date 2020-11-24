package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginServiceProxy;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class LoginPresenterTest {
    private LoginRequest request;
    private LoginRegisterResponse response;
    private LoginServiceProxy mockLoginServiceProxy;
    private LoginPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new LoginRequest(currentUser.getUsername(), "password");
        response = new LoginRegisterResponse(currentUser, new AuthToken("token123123123"));

        // Create a mock FollowingService
        mockLoginServiceProxy = Mockito.mock(LoginServiceProxy.class);
        Mockito.when(mockLoginServiceProxy.login(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginServiceProxy);
    }

    @Test
    public void testRegister_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginServiceProxy.login(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.login(request));
    }

    @Test
    public void testRegister_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockLoginServiceProxy.login(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.login(request));
    }
}
