package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * The presenter for the unfollow functionality of the application.
 */
public class UnfollowPresenter extends Presenter {

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public UnfollowPresenter(View view) {
        this.view = view;
    }

    public Response unfollow(FollowUnfollowRequest request) {
        UnfollowService unfollowService = getUnfollowService();
        return unfollowService.unfollow(request);
    }

    /**
     * Returns an instance of {@link UnfollowService}. Allows mocking of the UnfollowService class
     * for testing purposes. All usages of UnfollowService should get their UnfollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UnfollowService getUnfollowService() {
        return new UnfollowService();
    }

}