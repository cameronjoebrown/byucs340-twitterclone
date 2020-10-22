package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class StoryPresenterTest {

    private FeedStoryRequest request;
    private FeedStoryResponse response;
    private StoryService mockStoryService;
    private StoryPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        LocalDateTime date1 = LocalDateTime.of(2020, Month.APRIL, 28, 20, 30);
        LocalDateTime date2 = LocalDateTime.of(2020, Month.JUNE, 28, 20, 30);
        LocalDateTime date3 = LocalDateTime.of(2020, Month.APRIL, 28, 5, 30);

        Status resultStatus1 = new Status("Tweet1", currentUser,
                date1);
        Status resultStatus2 = new Status("Tweet2", currentUser,
                date2);
        Status resultStatus3 = new Status("This is tweet 3", currentUser,
                date3);

        request = new FeedStoryRequest(currentUser, 3, null);
        response = new FeedStoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);

        // Create a mock FollowingService
        mockStoryService = Mockito.mock(StoryService.class);
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        // Wrap a FollowingPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);
    }

    @Test
    public void testGetStory_returnsServiceResult() throws IOException {
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getStory(request));
    }

    @Test
    public void testGetStory_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockStoryService.getStory(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> presenter.getStory(request));
    }
}
