package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, PostStatusResponse> {
    private final PostStatusPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void postStatusSuccessful(PostStatusResponse response);
        void postStatusUnsuccessful(PostStatusResponse response);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to post a status.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public PostStatusTask(PostStatusPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected PostStatusResponse doInBackground(PostStatusRequest... postStatusRequests) {
        PostStatusResponse response = null;
        try {
            response = presenter.postStatus(postStatusRequests[0]);
        } catch (IOException | TweeterRemoteException e) {
            exception = e;
        }
        return response;
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(PostStatusRequest...)} method) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(PostStatusResponse response) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(response.isSuccess()) {
            observer.postStatusSuccessful(response);
        } else {
            observer.postStatusUnsuccessful(response);
        }
    }
}
