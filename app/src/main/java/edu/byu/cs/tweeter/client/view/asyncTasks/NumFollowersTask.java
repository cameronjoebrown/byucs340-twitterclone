package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;

public class NumFollowersTask extends AsyncTask<NumFollowsRequest, Void, NumFollowsResponse> {

    private final FollowerPresenter presenter;
    private final Observer observer;
    private Exception exception;


    /**
     * Creates an instance.
     *  @param presenter the presenter from whom this task should complete a follow operation
     * @param observer the observer who wants to be notified when this task completes.
     */
    public NumFollowersTask(FollowerPresenter presenter, Observer observer) {
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
        void followerCountSuccessful(NumFollowsResponse response);
        void handleException(Exception exception);
    }

    @Override
    protected NumFollowsResponse doInBackground(NumFollowsRequest... numFollowsRequests) {
        NumFollowsResponse response = null;
        try {
            response = presenter.getNumFollowers(numFollowsRequests[0]);
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
    protected void onPostExecute(NumFollowsResponse response) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.followerCountSuccessful(response);
        }
    }
}
