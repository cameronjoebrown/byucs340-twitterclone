package edu.byu.cs.tweeter.server.util;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FollowerDAO;
import edu.byu.cs.tweeter.server.dao.RegisterDAO;

/**
 * This class is for testing purposes only. It generates users and adds them
 * to the database as well as making all of these users follow one specific user.
 */
public class DatabaseGenerator {
    private final static int NUM_USERS = 10000;

    public static void generateData() {

        List<User> users = new ArrayList<>();
        List<Follow> follows = new ArrayList<>();

        // Generate the users
        for(int i = 0; i < NUM_USERS; i++) {
            String username = "@TestUser";
            String firstName = "Test";
            String lastName = "User" + i;
            String profilePicUrl = "https://cbrown-profile-images.s3.us-west-2.amazonaws.com/images/%40BobBobson.png";
            User newUser = new User(firstName,lastName,username,profilePicUrl);
            users.add(newUser);

            // Make each user follow the user @Test and @Test follow them
            Follow follow1 = new Follow("@BobBobson", username);
            Follow follow2 = new Follow(username, "@BobBobson");
            follows.add(follow1);
            follows.add(follow2);

        }

        // Add users to database
        RegisterDAO registerDAO = new RegisterDAO();
        registerDAO.addUsers(users);

        // Add follows to database
        FollowerDAO followerDAO = new FollowerDAO();
        followerDAO.addFollows(follows);
    }
}
