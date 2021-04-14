package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.ViewUserService;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

/**
 * Contains the business logic for getting a user.
 */
public class ViewUserServiceImpl implements ViewUserService {
    @Override
    public ViewUserResponse viewUser(ViewUserRequest request) {
        return getViewUserDAO().viewUser(request);
    }

    /**
     * Returns an instance of {@link UserDAO}. Allows mocking of the DAO class
     * for testing purposes. All usages of DAO should get their DAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UserDAO getViewUserDAO() {
        return new UserDAO();
    }
}
