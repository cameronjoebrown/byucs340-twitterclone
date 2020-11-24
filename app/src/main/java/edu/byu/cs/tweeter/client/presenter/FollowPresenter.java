package edu.byu.cs.tweeter.client.presenter;


import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FollowServiceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * The presenter for the follow functionality of the application.
 */
public class FollowPresenter {
    private final View view;

    /**
     * The interface by which this presenter sends notifications to it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public FollowPresenter(View view) {
        this.view = view;
    }

    public Response follow(NumFollowsRequest request) throws IOException, TweeterRemoteException {
        FollowServiceProxy followServiceProxy = getFollowService();
        return followServiceProxy.follow(request);
    }

    /**
     * Returns an instance of {@link FollowServiceProxy}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowServiceProxy getFollowService() {
        return new FollowServiceProxy();
    }
}