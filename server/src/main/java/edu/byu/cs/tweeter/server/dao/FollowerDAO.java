package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;

/**
 * A DAO for accessing 'follower' data from the database.
 */
public class FollowerDAO {

    // This is the hard coded followee data returned by the 'getFollowees()' and 'getFollowers()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);


    /**
     * Gets the count of users from the database that the user specified is following. The
     * current implementation uses generated data and doesn't actually access a database.
     *
     * @param followee the User whose count of how many following is desired.
     * @return said count.
     */
    public NumFollowsResponse getFollowerCount(String followee) {
        // TODO: uses the dummy data.  Replace with a real implementation.
        assert followee != null;
        return new NumFollowsResponse(getDummyFollowers().size());
    }

    /**
     * Returns the followers of the user specified. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followers are to be returned and any
     *                other information required to satisfy the request.
     * @return the follower response.
     */
    public FollowerResponse getFollowers(FollowerRequest request) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("follows");
        Index index = table.getIndex("follows_index");

        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#followee_handle", "followee_handle");

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":followee_handle", request.getFollowee());

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#followee_handle = :followee_handle")
                .withNameMap(nameMap)
                .withValueMap(valueMap)
                .withMaxResultSize(10)
                .withScanIndexForward(true);


        if (request.getLastFollower() != null && !request.getLastFollower().equals("")) {
            querySpec.withExclusiveStartKey("followee_handle", request.getFollowee(),
                    "follower_handle", request.getLastFollower());
        }
        ItemCollection<QueryOutcome> items = index.query(querySpec);
        Iterator<Item> iterator = items.iterator();
        Item item;
        List<User> responseFollowers = new ArrayList<>();

        while (iterator.hasNext()) {
            item = iterator.next();

            ViewUserDAO viewUserDAO = new ViewUserDAO();
            ViewUserRequest viewUserRequest = new ViewUserRequest(item.getString("follower_handle"));
            User follower = viewUserDAO.viewUser(viewUserRequest).getUser();
            responseFollowers.add(follower);
        }
        // Check for more pages
        QueryOutcome outcome = items.getLastLowLevelResult();
        boolean hasMorePages = outcome.getQueryResult().getLastEvaluatedKey() != null;
        return new FollowerResponse(responseFollowers, hasMorePages);

    }

    /**
     * Returns the list of dummy followee/follower data. This is written as a separate method to allow
     * mocking of the followees/follower.
     *
     * @return the generator.
     */
    List<User> getDummyFollowers() {
        return Arrays.asList(user1, user2, user3, user4, user5, user6, user7,
                user8, user9, user10, user11, user12, user13, user14, user15, user16, user17, user18,
                user19, user20);
    }

    /**
     * This method allows multiple follows to be added at once. This will mostly be used
     * for testing purposes.
     *
     * @param follows the list of follows to be added
     */
    public void addFollows(List<Follow> follows) {
        TableWriteItems items = new TableWriteItems("follows");

        for (Follow follow : follows) {
            Item item = new Item()
                    .withPrimaryKey("follower_handle", follow.getFollower(),"followee_handle", follow.getFollowee());
            items.addItemToPut(item);

            // Add in batches of 25
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                writeBatch(items);
                items = new TableWriteItems("follows");
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

}
