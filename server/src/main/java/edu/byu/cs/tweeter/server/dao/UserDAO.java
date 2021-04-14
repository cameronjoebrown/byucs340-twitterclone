package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;
import edu.byu.cs.tweeter.model.util.ByteArrayUtils;

/**
 * A DAO for accessing and manipulating data in the users table
 */
public class UserDAO {
    public LoginRegisterResponse register(RegisterRequest request) {

        // Save image to S3 bucket
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();

        byte[] imageBytes;
        try {
            imageBytes = ByteArrayUtils.bytesFromUrl(request.getProfilePicURL());
        } catch (IOException e) {
            throw new RuntimeException("Unable to load image");
        }

        InputStream stream = new ByteArrayInputStream(imageBytes);

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(imageBytes.length);
        meta.setContentType("image/png");

        s3Client.putObject(new PutObjectRequest(
                "cbrown-profile-images", "images/" + request.getUsername() + ".png", stream, meta)
                .withCannedAcl(CannedAccessControlList.Private));

        String imageUrl = s3Client.getUrl("cbrown-profile-images", "images/" + request.getUsername() + ".png").toString();

        // Open connection to DynamoDB and save user to users table
        AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);

        Table table = dynamoDB.getTable("users");

        String salt = PasswordHasher.getSalt();
        System.out.println("Salt: " + salt);

        String securePassword = PasswordHasher.getSecurePassword(request.getPassword(), salt);
        System.out.println("Secured Password: " + securePassword);

        try {
            System.out.println("Adding a new item...");

            table.putItem(new Item().withPrimaryKey("username",
                    request.getUsername()).withString("firstName", request.getFirstName())
                    .withString("lastName", request.getLastName())
                    .withString("imageUrl", imageUrl)
                    .withString("password", securePassword)
                    .withString("salt", salt)
                    .withNumber("followerCount", 0)
                    .withNumber("followeeCount", 0));

            User user = new User(request.getFirstName(), request.getLastName(),
                    request.getUsername(), imageUrl);

            AuthTokenDAO authTokenDAO = new AuthTokenDAO();
            AuthToken authToken = authTokenDAO.createAuthToken();
            return new LoginRegisterResponse(user, authToken);

        }
        catch (Exception e) {
            System.err.println("Unable to create user: " + request.getUsername());
            System.err.println(e.getMessage());
            return new LoginRegisterResponse(e.getMessage());
        }
    }

    /**
     * This method allows multiple users to be added at once. This will mostly be used
     * for testing purposes.
     *
     * @param users the list of users to be added
     */
    public void addUsers(List<User> users) {
        TableWriteItems items = new TableWriteItems("users");

        for (User user : users) {
            Item item = new Item()
                    .withPrimaryKey("username", user.getUsername())
                    .withString("firstName", user.getFirstName())
                    .withString("lastName", user.getLastName())
                    .withString("imageUrl", user.getImageUrl())
                    .withNumber("followerCount", 0)
                    .withNumber("followeeCount", 0);
            items.addItemToPut(item);

            // Add in batches of 25
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                writeBatch(items);
                items = new TableWriteItems("users");
            }
        }

        //
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            writeBatch(items);
        }
    }

    private void writeBatch(TableWriteItems items) {
        // Open connection to DynamoDB and save user to users table
        AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);

        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);

        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
        }
    }

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
