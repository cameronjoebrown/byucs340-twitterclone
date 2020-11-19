package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class LoginPresenterTest {
    private LoginRequest request;
    private LoginRegisterResponse response;
    private LoginService mockLoginService;
    private LoginPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new LoginRequest(currentUser.getUsername(), "password");
        response = new LoginRegisterResponse(currentUser, new AuthToken());

        // Create a mock FollowingService
        mockLoginService = Mockito.mock(LoginService.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    public void testRegister_returnsServiceResult() throws IOException {
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.login(request));
    }

    @Test
    public void testRegister_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockLoginService.login(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.login(request));
    }
}
