package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.security.SecureRandom;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class AuthTokenDAO {
    public AuthToken createAuthToken() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2)
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("authTokens");

        System.out.println("Adding a new auth token...");

        // Create a random string for auth token
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        String tokenString = Arrays.toString(bytes);
        AuthToken authToken;

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("token", tokenString));
            authToken = new AuthToken(tokenString);
            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

        }
        catch (Exception e) {
            System.err.println("Unable to add auth token");
            System.err.println(e.getMessage());
            throw new RuntimeException("Internal Server Error" + e.getMessage());
        }

        return authToken;
    }
}
