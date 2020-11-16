package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

/**
 * An {@link AsyncTask} for retrieving story for a user.
 */
public class GetStoryTask extends AsyncTask<FeedStoryRequest, Void, FeedStoryResponse> {
    private final StoryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void storyRetrieved(FeedStoryResponse response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve statuses.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetStoryTask(StoryPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve statuses. This method is
     * invoked indirectly by calling {@link #execute(FeedStoryRequest...)}.
     *
     * @param storyRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FeedStoryResponse doInBackground(FeedStoryRequest... storyRequests) {

        FeedStoryResponse response = null;

        try {
            response = presenter.getStory(storyRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param storyResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FeedStoryResponse storyResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.storyRetrieved(storyResponse);
        }
    }
}
