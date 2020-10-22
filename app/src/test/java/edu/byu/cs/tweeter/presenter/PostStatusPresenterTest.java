package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;


public class PostStatusPresenterTest {
    private PostStatusRequest request;
    private PostStatusResponse response;
    private PostStatusService mockPostStatusService;
    private PostStatusPresenter presenter;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        LocalDateTime date1 = LocalDateTime.of(2020, Month.APRIL, 28, 20, 30);
        Status status1 = new Status("This is a status", currentUser, date1);

        request = new PostStatusRequest(status1.getStatusText(), currentUser, date1);
        response = new PostStatusResponse(status1);

        // Create a mock FollowingService
        mockPostStatusService = Mockito.mock(PostStatusService.class);
        Mockito.when(mockPostStatusService.postStatus(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new PostStatusPresenter(new PostStatusPresenter.View() {}));
        Mockito.when(presenter.getPostStatusService()).thenReturn(mockPostStatusService);
    }

    @Test
    public void testPostStatus_returnsServiceResult() {
        Mockito.when(mockPostStatusService.postStatus(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.postStatus(request));
    }
}
