package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

/**
 * A DAO for adding new statuses to the database.
 */
public class PostStatusDAO {
    public PostStatusResponse postStatus(PostStatusRequest request) {
        return new PostStatusResponse(new Status(request.getStatusText(), request.getUser(), request.getTimeStamp()));
    }
}
