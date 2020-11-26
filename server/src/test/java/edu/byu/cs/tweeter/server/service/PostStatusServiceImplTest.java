package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.dao.PostStatusDAO;

public class PostStatusServiceImplTest {
    private PostStatusRequest validRequest;
    private PostStatusRequest invalidRequest;

    private PostStatusResponse successResponse;
    private PostStatusResponse failureResponse;

    private PostStatusServiceImpl postStatusServiceImplSpy;

    /**
     * Create a PostStatusService spy that uses a mock PostStatusDAO to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        // Setup request objects to use in the tests
        User user1 = new User("Bob", "Joe", "@bobjoe",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        String date1 = "2020-04-28 20:30";

        validRequest = new PostStatusRequest("This is a tweet", user1.getUsername(), date1);
        invalidRequest = new PostStatusRequest(null,null, null);

        // Setup a mock PostStatusDAO that will return known responses
        successResponse = new PostStatusResponse(new Status(validRequest.getStatusText(),
                user1, validRequest.getTimeStamp()));
        PostStatusDAO mockPostStatusDAO = Mockito.mock(PostStatusDAO.class);
        Mockito.when(mockPostStatusDAO.postStatus(validRequest)).thenReturn(successResponse);

        failureResponse = new PostStatusResponse("An exception occured");
        Mockito.when(mockPostStatusDAO.postStatus(invalidRequest)).thenReturn(failureResponse);

        // Create a PostStatusService instance and wrap it with a spy that will use the mock service
        postStatusServiceImplSpy = Mockito.spy(new PostStatusServiceImpl());
        Mockito.when(postStatusServiceImplSpy.getPostStatusDAO()).thenReturn(mockPostStatusDAO);

    }

    /**
     * Verify that for successful requests the {@link PostStatusServiceImpl#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link PostStatusDAO}.
     * .
     */
    @Test
    public void testPostStatus_validRequest_correctResponse() {
        Response response = postStatusServiceImplSpy.postStatus(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for invalid requests the {@link PostStatusServiceImpl#postStatus(PostStatusRequest)}
     * method returns the same result as the {@link PostStatusDAO}.
     *
     */
    @Test
    public void testPostStatus_invalidRequest_postStatusUnsuccessful() {
        Response response = postStatusServiceImplSpy.postStatus(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
