package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class LoginTask extends AsyncTask<LoginRequest, Void, LoginRegisterResponse> {

    private final LoginPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void loginSuccessful(LoginRegisterResponse response);
        void loginUnsuccessful(LoginRegisterResponse response);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to login.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public LoginTask(LoginPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(LoginRequest...)}.
     *
     * @param loginRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected LoginRegisterResponse doInBackground(LoginRequest... loginRequests) {
        LoginRegisterResponse response = null;

        try {
            response = presenter.login(loginRequests[0]);

            if(response.isSuccess()) {
                loadImage(response.getUser());
            }
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Loads the profile image for the user.
     *
     * @param user the user whose profile image is to be loaded.
     */
    private void loadImage(User user) {
        try {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
        }
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(LoginRequest...)} method) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(LoginRegisterResponse response) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(response.isSuccess()) {
            observer.loginSuccessful(response);
        } else {
            observer.loginUnsuccessful(response);
        }
    }
}
