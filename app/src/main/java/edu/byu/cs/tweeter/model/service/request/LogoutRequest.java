package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class LogoutRequest {

    /**
     * the authToken of the user to be logged out
     */
    private final AuthToken authToken;

    /**
     * Creates an instance.
     *
     * @param authToken the authToken of the user to be logged out
     */
    LogoutRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the authToken of the user to be logged out by this request.
     *
     * @return the authToken
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

}
