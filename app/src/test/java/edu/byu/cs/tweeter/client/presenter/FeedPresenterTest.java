package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class FeedPresenterTest {
    private FeedStoryRequest request;
    private FeedStoryResponse response;
    private FeedService mockFeedService;
    private FeedPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        LocalDateTime date1 = LocalDateTime.of(2020, Month.APRIL, 28, 20, 30);
        LocalDateTime date2 = LocalDateTime.of(2020, Month.JUNE, 28, 20, 30);
        LocalDateTime date3 = LocalDateTime.of(2020, Month.APRIL, 28, 5, 30);

        Status resultStatus1 = new Status("I am the coolest in the world", resultUser1, date1);
        Status resultStatus2 = new Status("Hanging out with @coolbob", resultUser2, date3);
        Status resultStatus3 = new Status("Nevermind, I'm no longer the coolest in the world.",
                resultUser3, date2);

        request = new FeedStoryRequest(currentUser, 3, null);
        response = new FeedStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);

        // Create a mock FollowingService
        mockFeedService = Mockito.mock(FeedService.class);
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedService);
    }

    @Test
    public void testGetFeed_returnsServiceResult() throws IOException {
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFeed(request));
    }

    @Test
    public void testGetFeed_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockFeedService.getFeed(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getFeed(request));
    }
}
