package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * A DAO for accessing 'login register' data from the database.
 */
public class LoginDAO {
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    public LoginRegisterResponse login(LoginRequest request) {
          String username = request.getUsername();
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
          return new LoginRegisterResponse(user, new AuthToken("token123123123"));
      }
}
