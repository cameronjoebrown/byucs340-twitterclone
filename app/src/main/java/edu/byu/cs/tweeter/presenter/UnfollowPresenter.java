package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.service.UnfollowService;
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

    public Response unfollow(FollowUnfollowRequest request) {
        UnfollowService unfollowService = new UnfollowService();
        return unfollowService.unfollow(request);
    }
}
