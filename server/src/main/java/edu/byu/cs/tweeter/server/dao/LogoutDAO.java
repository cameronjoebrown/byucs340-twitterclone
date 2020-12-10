package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * A DAO for accessing 'logout' data from the database.
 */
public class LogoutDAO {
    public Response logout(LogoutRequest request) {
        AuthTokenDAO authTokenDAO = new AuthTokenDAO();
        return authTokenDAO.deleteAuthToken(request.getAuthToken());
    }
}
