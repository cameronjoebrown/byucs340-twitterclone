package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class Service {

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }

    /**
     * Loads the profile image data for the user.
     *
     * @param user the user whose profile image data is to be loaded.
     */
    void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    /**
     * Loads the profile image data for each user included in the list.
     *
     * @param users the users whose profile images need to be loaded.
     */
    void loadImages(List<User> users) throws IOException {
        for(User user : users) {
            loadImage(user);
        }
    }

    /**
     * Loads the profile image data for each status included in the list.
     *
     * @param statuses the statuses for which profile images need to be loaded.
     */
    void loadStatusImages(List<Status> statuses) throws IOException {
        for(Status status : statuses) {
            loadImage(status.getUser());
        }
    }
}
