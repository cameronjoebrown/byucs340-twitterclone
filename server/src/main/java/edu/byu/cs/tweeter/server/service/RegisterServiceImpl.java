package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class RegisterServiceImpl implements RegisterService {

    @Override
    public LoginRegisterResponse register(RegisterRequest request) {
        return getUserDAO().register(request);
    }

    /**
     * Returns an instance of {@link UserDAO}. Allows mocking of the DAO class
     * for testing purposes. All usages of DAO should get their DAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UserDAO getUserDAO() {
        return new UserDAO();
    }
}
