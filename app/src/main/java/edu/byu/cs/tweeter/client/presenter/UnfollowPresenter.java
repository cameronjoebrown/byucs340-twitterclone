package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.UnfollowServiceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * The presenter for the unfollow functionality of the application.
 */
public class UnfollowPresenter {
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
    public UnfollowPresenter(View view) {
        this.view = view;
    }

    public Response unfollow(FollowUnfollowRequest request) throws IOException, TweeterRemoteException {
        UnfollowServiceProxy unfollowServiceProxy = getUnfollowService();
        return unfollowServiceProxy.unfollow(request);
    }

    /**
     * Returns an instance of {@link UnfollowServiceProxy}. Allows mocking of the UnfollowService class
     * for testing purposes. All usages of UnfollowService should get their UnfollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UnfollowServiceProxy getUnfollowService() {
        return new UnfollowServiceProxy();
    }

}