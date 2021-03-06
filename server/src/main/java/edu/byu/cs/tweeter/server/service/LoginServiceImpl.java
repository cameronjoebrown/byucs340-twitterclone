package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.server.dao.LoginDAO;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginRegisterResponse login(LoginRequest request) {
        return getLoginDAO().login(request);
    }

    /**
     * Returns an instance of {@link LoginDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    LoginDAO getLoginDAO() {
        return new LoginDAO();
    }
}
