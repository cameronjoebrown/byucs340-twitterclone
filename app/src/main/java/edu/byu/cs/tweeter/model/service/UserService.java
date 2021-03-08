package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public abstract class UserService extends Service {
    public void runService() throws IOException {
        boolean success = runMethod(getServerFacade());
        if(success) {
            load();
        }
    }

    abstract boolean runMethod(ServerFacade serverFacade);
    abstract void load() throws IOException;

    /**
     * Loads the profile image data for the user.
     *
     * @param user the user whose profile image data is to be loaded.
     */
    public void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }
}
