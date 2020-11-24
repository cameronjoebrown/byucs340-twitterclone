package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.Response;

/**
 * A DAO for updating follows in the database.
 */
public class UnfollowDAO {
    public Response unfollow(NumFollowsRequest request) {
        return new Response(true);
    }
}
