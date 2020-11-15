package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;

/**
 * An {@link AsyncTask} for retrieving feed for a user.
 */
public class GetFeedTask extends AsyncTask<FeedStoryRequest, Void, FeedStoryResponse> {
    private final FeedPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void feedRetrieved(FeedStoryResponse response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve statuses.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetFeedTask(FeedPresenter presenter, GetFeedTask.Observer observer) {
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
     * @param feedRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FeedStoryResponse doInBackground(FeedStoryRequest... feedRequests) {

        FeedStoryResponse response = null;

        try {
            response = presenter.getFeed(feedRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param feedResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FeedStoryResponse feedResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.feedRetrieved(feedResponse);
        }
    }
}
