package edu.byu.cs.tweeter.presenter;


import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
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

    public Response follow(FollowUnfollowRequest request) {
        FollowService followService = geFollowService();
        return followService.follow(request);
    }

    /**
     * Returns an instance of {@link FollowService}. Allows mocking of the FollowService class
     * for testing purposes. All usages of FollowService should get their FollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowService geFollowService() {
        return new FollowService();
    }
}