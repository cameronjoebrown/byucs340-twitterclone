package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * The presenter for the feed functionality of the application.
 */
public class FeedPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public FeedPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a Get Feed Request
     *
     * @param request the feed request
     */
    public FeedStoryResponse getFeed(FeedStoryRequest request) throws IOException {
        FeedService feedService = getFeedService();
        return feedService.getFeed(request);
    }

    /**
     * Returns an instance of {@link FeedService}. Allows mocking of the FeedService class
     * for testing purposes. All usages of FeedService should get their FeedService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FeedService getFeedService() {
        return new FeedService();
    }

}
