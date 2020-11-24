package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.UnfollowPresenter;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class UnfollowTask extends AsyncTask<NumFollowsRequest, Void, Response> {
    private final UnfollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void unfollowSuccessful(Response response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *  @param presenter the presenter from whom this task should complete a unfollow operation
     * @param observer the observer who wants to be notified when this task completes.
     */
    public UnfollowTask(UnfollowPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to follow a user. This method is
     * invoked indirectly by calling {@link #execute(NumFollowsRequest...)}.
     *
     * @param unfollowRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected Response doInBackground(NumFollowsRequest... unfollowRequests) {
        Response response = null;
        try {
            return presenter.unfollow(unfollowRequests[0]);
        } catch (IOException | TweeterRemoteException e) {
            exception = e;
        }
        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(Response response) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.unfollowSuccessful(response);
        }
    }
}
