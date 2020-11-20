package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.CreateStatusRequest;
import edu.byu.cs.tweeter.model.service.response.CreateStatusResponse;

/**
 * A DAO for adding new statuses to the database.
 */
public class PostStatusDAO {
    /**
     * This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    private FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

    public CreateStatusResponse createStatus(CreateStatusRequest request) {
        Status newStatus = new Status(request.getStatusText(),
                request.getStatusOwner(), request.getTimeStamp());
        getFollowGenerator().addStatus(newStatus);
        return new CreateStatusResponse(newStatus);
    }
}
