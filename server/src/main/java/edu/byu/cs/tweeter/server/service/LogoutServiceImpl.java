package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;

/**
 * Contains the business logic for logout.
 */
public class LogoutServiceImpl implements LogoutService {
    @Override
    public Response logout(LogoutRequest request) {
        return getAuthTokenDAO().logout(request);
    }

    /**
     * Returns an instance of {@link AuthTokenDAO}. Allows mocking of the DAO class
     * for testing purposes. All usages of DAO should get their DAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
