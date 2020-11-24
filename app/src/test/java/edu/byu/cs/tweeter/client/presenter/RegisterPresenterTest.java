package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.RegisterServiceProxy;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class RegisterPresenterTest {
    private RegisterRequest request;
    private LoginRegisterResponse response;
    private RegisterServiceProxy mockRegisterServiceProxy;
    private RegisterPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new RegisterRequest(currentUser.getUsername(), "password", currentUser.getFirstName(), currentUser.getLastName(), currentUser.getImageUrl());
        response = new LoginRegisterResponse(currentUser, new AuthToken("cooladfsdfdsfa"));

        // Create a mock FollowingService
        mockRegisterServiceProxy = Mockito.mock(RegisterServiceProxy.class);
        Mockito.when(mockRegisterServiceProxy.register(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterServiceProxy);
    }

    @Test
    public void testRegister_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterServiceProxy.register(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.register(request));
    }

    @Test
    public void testRegister_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockRegisterServiceProxy.register(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.register(request));
    }
}
