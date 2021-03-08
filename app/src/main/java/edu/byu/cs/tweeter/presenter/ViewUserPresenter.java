package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.ViewUserService;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

/**
 * The presenter for the view user functionality of the application.
 */
public class ViewUserPresenter extends Presenter {

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public ViewUserPresenter(View view) {
        this.view = view;
    }

    public ViewUserResponse viewUser(ViewUserRequest request) throws IOException {
        ViewUserService viewUserService = getViewUserService();
        return viewUserService.viewUser(request);
    }

    /**
     * Returns an instance of {@link ViewUserService}. Allows mocking of the ViewUserService class
     * for testing purposes. All usages of ViewUserService should get their ViewUserService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    ViewUserService getViewUserService() {
        return new ViewUserService();
    }

}
