package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

/**
 * A DAO for accessing 'logout' data from the database.
 */
public class LogoutDAO {
    public LogoutResponse logoutUser(LogoutRequest request) {
        boolean foundAuthToken = true;
        if(foundAuthToken) {
            return new LogoutResponse(true);
        }
        else {
            return new LogoutResponse(false, "Failed to logout user. User may already" +
                    "have been logged out");
        }
    }
}
