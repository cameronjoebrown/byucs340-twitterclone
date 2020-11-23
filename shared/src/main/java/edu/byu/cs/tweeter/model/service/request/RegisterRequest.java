package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a request to have
 * the server register a user.
 */
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String profilePicURL;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private RegisterRequest() {}

    public RegisterRequest(String username, String password, String firstName,
                           String lastName, String profilePicURL) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicURL = profilePicURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePicURL() {
        return profilePicURL;
    }
}

