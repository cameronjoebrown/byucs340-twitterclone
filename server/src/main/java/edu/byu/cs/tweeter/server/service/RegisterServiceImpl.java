package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.server.dao.RegisterDAO;

public class RegisterServiceImpl implements RegisterService {

    @Override
    public LoginRegisterResponse register(RegisterRequest request) {
        return getRegisterDAO().register(request);
    }

    /**
     * Returns an instance of {@link RegisterDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    RegisterDAO getRegisterDAO() {
        return new RegisterDAO();
    }
}
