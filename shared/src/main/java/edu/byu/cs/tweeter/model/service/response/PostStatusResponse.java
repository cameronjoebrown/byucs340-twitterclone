package edu.byu.cs.tweeter.model.service.response;


import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusResponse extends Response {

    Status status;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public PostStatusResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param status the status that was posted
     *
     **/
    public PostStatusResponse(Status status) {
        super(true, null);
        this.status = status;
    }

    /**
     * Returns the requested status
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }
}
