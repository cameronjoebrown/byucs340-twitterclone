package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.LogoutServiceProxy;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * The presenter for the logout functionality of the application.
 */
public class LogoutPresenter {
    private final View view;
    /**
     * The interface by which this presenter sends notifications to it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LogoutPresenter(View view) {
        this.view = view;
    }

    public Response logout(LogoutRequest request) throws IOException {
        LogoutServiceProxy logoutServiceProxy = getLogoutService();
        return logoutServiceProxy.logout(request);
    }

    /**
     * Returns an instance of {@link LogoutServiceProxy}. Allows mocking of the LogoutService class
     * for testing purposes. All usages of LogoutService should get their LogoutService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    LogoutServiceProxy getLogoutService() {
        return new LogoutServiceProxy();
    }
}
