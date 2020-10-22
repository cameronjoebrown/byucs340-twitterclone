package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutPresenterTest {
    private LogoutRequest request;
    private Response response;
    private LogoutService mockLogoutService;
    private LogoutPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new LogoutRequest(new AuthToken());
        response = new Response(true);

        // Create a mock FollowingService
        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
    }

    @Test
    public void testLogout_returnsServiceResult() throws IOException {
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.logout(request));
    }

    @Test
    public void testLogout_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockLogoutService.logout(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.logout(request));
    }
}
