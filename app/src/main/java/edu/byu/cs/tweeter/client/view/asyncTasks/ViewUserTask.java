package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.ViewUserPresenter;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

public class ViewUserTask extends AsyncTask<ViewUserRequest, Void, ViewUserResponse> {
    private final ViewUserPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void userRetrieved(ViewUserResponse viewUserResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve a user.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public ViewUserTask(ViewUserPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve a user. This method is
     * invoked indirectly by calling {@link #execute(ViewUserRequest...)}.
     *
     * @param viewUserRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected ViewUserResponse doInBackground(ViewUserRequest... viewUserRequests) {

        ViewUserResponse response = null;

        try {
            response = presenter.viewUser(viewUserRequests[0]);
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param viewUserResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(ViewUserResponse viewUserResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.userRetrieved(viewUserResponse);
        }
    }

}
