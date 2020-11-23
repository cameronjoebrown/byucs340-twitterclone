package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * A DAO for accessing 'register' data from the database.
 */
public class RegisterDAO {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    public LoginRegisterResponse register(RegisterRequest request) {
        String username = request.getUsername();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        User user = new User(firstName, lastName, username,
                MALE_IMAGE_URL);
        return new LoginRegisterResponse(user, new AuthToken("cooladfsdfdsfa"));
    }
}
