package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * A DAO for updating follows in the database.
 */
public class FollowDAO {
    public Response follow(FollowUnfollowRequest request) {
        return new Response(true);
    }
}
