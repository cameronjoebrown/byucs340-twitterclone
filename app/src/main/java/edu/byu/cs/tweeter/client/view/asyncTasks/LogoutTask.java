package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.client.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, Response> {

    private final LogoutPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void logoutSuccessful(Response response);
        void logoutUnsuccessful(Response response);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to logout.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public LogoutTask(LogoutPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to logout the user. This method is
     * invoked indirectly by calling {@link #execute(LogoutRequest...)}.
     *
     * @param logoutRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected Response doInBackground(LogoutRequest... logoutRequests) {
        Response response = null;
        try {
            response = presenter.logout(logoutRequests[0]);

        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(LogoutRequest...)} method) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(Response response) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(response.isSuccess()) {
            observer.logoutSuccessful(response);
        } else {
            observer.logoutUnsuccessful(response);
        }
    }
}
