package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;

import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.Response;

public class PostStatusServiceTest {
    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusService postStatusServiceSpy;

    /**
     * Create a PostStatusService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        LocalDateTime date1 = LocalDateTime.of(2020, Month.APRIL, 28, 20, 30);

        validRequest = new PostStatusRequest("This is a tweet", user1, date1);
        invalidRequest = new PostStatusRequest(null,null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostStatusResponse(new Status(validRequest.getStatusText(),
                validRequest.getUser(), validRequest.getTimeStamp()));
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.postStatus(validRequest)).thenReturn(successResponse);

        failureResponse = new PostStatusResponse("An exception occured");
        Mockito.when(mockServerFacade.postStatus(invalidRequest)).thenReturn(failureResponse);

        // Create a PostStatusService instance and wrap it with a spy that will use the mock service
        postStatusServiceSpy = Mockito.spy(new PostStatusService());
        Mockito.when(postStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    /**
     * Verify that for successful requests the {@link PostStatusService#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     */
    @Test
    public void testPostStatus_validRequest_correctResponse() {
        Response response = postStatusServiceSpy.postStatus(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link PostStatusService#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     */
    @Test
    public void testPostStatus_invalidRequest_postStatusUnsuccessful() {
        Response response = postStatusServiceSpy.postStatus(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
