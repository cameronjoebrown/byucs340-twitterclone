package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

/**
 * A DAO for accessing 'login register' data from the database.
 */
public class LoginDAO {
    public LoginRegisterResponse login(LoginRequest request) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("users");


        GetItemSpec spec = new GetItemSpec().withPrimaryKey("username", request.getUsername());
        Item item;

        try {
            System.out.println("Attempting to read the item...");
            item = table.getItem(spec);
            if(item == null) {
                throw new RuntimeException("Username does not exist");
            }

            // Check the password
            String securePassword = item.getString("password");
            String salt = item.getString("salt");
            System.out.println(salt + " " + securePassword);
            String regeneratedPasswordToVerify = PasswordHasher.getSecurePassword(request.getPassword(), salt);
            if (!securePassword.equals(regeneratedPasswordToVerify)) {
                throw new RuntimeException("Password doesn't match");
            }

            // Create user to be returned
            User user = new User(item.getString("firstName"), item.getString("lastName"),
                    request.getUsername(), item.getString("imageUrl"));

            AuthTokenDAO authTokenDAO = new AuthTokenDAO();
            AuthToken authToken = authTokenDAO.createAuthToken();
            return new LoginRegisterResponse(user, authToken);

        } catch (Exception e) {
            System.err.println("Unable to login user: " + request.getUsername());
            System.err.println(e.getMessage());
            return new LoginRegisterResponse(e.getMessage());
        }

    }

}
