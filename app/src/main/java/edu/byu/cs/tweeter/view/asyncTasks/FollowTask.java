package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.FollowPresenter;

public class FollowTask extends AsyncTask<FollowUnfollowRequest, Void, Response> {
    private final FollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followSuccessful(Response response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should complete a follow operation
     * @param observer the observer who wants to be notified when this task completes.
     */
    public FollowTask(FollowPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to follow a user. This method is
     * invoked indirectly by calling {@link #execute(FollowUnfollowRequest...)}.
     *
     * @param followRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected Response doInBackground(FollowUnfollowRequest... followRequests) {

        return presenter.follow(followRequests[0]);
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
            observer.followSuccessful(response);
        }
    }
}
