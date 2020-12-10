package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.model.util.ByteArrayUtils;

/**
 * A DAO for accessing 'register' data from the database.
 */
public class RegisterDAO {
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
                    .withString("salt", salt));

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
}
