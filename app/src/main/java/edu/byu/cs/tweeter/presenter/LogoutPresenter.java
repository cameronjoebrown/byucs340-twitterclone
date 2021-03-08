package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * The presenter for the logout functionality of the application.
 */
public class LogoutPresenter extends Presenter {

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LogoutPresenter(View view) {
        this.view = view;
    }

    public Response logout(LogoutRequest request) throws IOException {
        LogoutService logoutService = getLogoutService();
        return logoutService.logout(request);
    }

    /**
     * Returns an instance of {@link LogoutService}. Allows mocking of the LogoutService class
     * for testing purposes. All usages of LogoutService should get their LogoutService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    LogoutService getLogoutService() {
        return new LogoutService();
    }
}
