package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.StoryServiceProxy;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * The presenter for the story functionality of the application.
 */
public class StoryPresenter {

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
    public StoryPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a Get Story Request
     *
     * @param request the story request
     */
    public FeedStoryResponse getStory(FeedStoryRequest request) throws IOException, TweeterRemoteException {
        StoryServiceProxy storyServiceProxy = getStoryService();
        return storyServiceProxy.getStory(request);
    }

    /**
     * Returns an instance of {@link StoryServiceProxy}. Allows mocking of the StoryService class
     * for testing purposes. All usages of StoryService should get their StoryService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StoryServiceProxy getStoryService() {
        return new StoryServiceProxy();
    }

}
