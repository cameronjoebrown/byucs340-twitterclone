package edu.byu.cs.tweeter.model.service.response;

public class NumFollowsResponse extends Response {
    private Integer followCount;
    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private NumFollowsResponse() {}

    public NumFollowsResponse(Integer followCount) {
        super(true, null);
        this.followCount = followCount;
    }

    public NumFollowsResponse(String message) {
        super(false, message);
    }

    public Integer getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }
}
