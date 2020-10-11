package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a request to have
 * the server register a user.
 */
public class RegisterRequest {
    private final String alias;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final String profilePicURL;

    public RegisterRequest(String alias, String password, String firstName,
                           String lastName, String profilePicURL) {
        this.alias = alias;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicURL = profilePicURL;
    }

    public String getAlias() {
        return alias;
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

