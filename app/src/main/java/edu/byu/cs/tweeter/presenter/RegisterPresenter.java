package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class RegisterPresenter extends Presenter {

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public RegisterPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a register request.
     *
     * @param registerRequest the request.
     */
    public LoginRegisterResponse register(RegisterRequest registerRequest) throws IOException {
        RegisterService registerService = getRegisterService();
        return registerService.register(registerRequest);
    }

    /**
     * Returns an instance of {@link RegisterService}. Allows mocking of the RegisterService class
     * for testing purposes. All usages of RegisterService should get their RegisterService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    RegisterService getRegisterService() {
        return new RegisterService();
    }
}
