package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.PostStatusServiceProxy;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * The presenter for the post status functionality of the application.
 */
public class PostStatusPresenter {
    private final View view;
    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    public PostStatusPresenter(View view) {
        this.view = view;
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        PostStatusServiceProxy postStatusServiceProxy = getPostStatusService();
        return postStatusServiceProxy.postStatus(request);
    }

    /**
     * Returns an instance of {@link PostStatusServiceProxy}. Allows mocking of the PostStatusService class
     * for testing purposes. All usages of PostStatusService should get their PostStatusService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    PostStatusServiceProxy getPostStatusService() {
        return new PostStatusServiceProxy();
    }
}
