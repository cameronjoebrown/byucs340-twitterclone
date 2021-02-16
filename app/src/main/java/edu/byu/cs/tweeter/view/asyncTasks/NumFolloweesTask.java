package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;

public class NumFolloweesTask extends AsyncTask<NumFollowsRequest, Void, NumFollowsResponse> {
    private final FollowingPresenter presenter;
    private final Observer observer;
    private Exception exception;


    /**
     * Creates an instance.
     *  @param presenter the presenter from whom this task should complete a follow operation
     * @param observer the observer who wants to be notified when this task completes.
     */
    public NumFolloweesTask(FollowingPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }
    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followeesCountSuccessful(NumFollowsResponse response);
        void handleException(Exception exception);
    }

    @Override
    protected NumFollowsResponse doInBackground(NumFollowsRequest... numFollowsRequests) {
        return presenter.getNumFollowees(numFollowsRequests[0]);
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(NumFollowsResponse response) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.followeesCountSuccessful(response);
        }
    }
}
