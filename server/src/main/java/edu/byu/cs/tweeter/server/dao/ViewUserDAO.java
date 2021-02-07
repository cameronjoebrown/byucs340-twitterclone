package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;

/**
 * A DAO for accessing 'view user' data from the database.
 */
public class ViewUserDAO {
    public ViewUserResponse viewUser(ViewUserRequest request) {
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

            // Create user to be returned
            User user = new User(item.getString("firstName"), item.getString("lastName"),
                    request.getUsername(), item.getString("imageUrl"));
            return new ViewUserResponse(user, true);

        } catch (Exception e) {
            System.err.println("Unable to find user: " + request.getUsername());
            System.err.println(e.getMessage());
            return new ViewUserResponse("Error: " + e.getMessage());
        }

    }

}
