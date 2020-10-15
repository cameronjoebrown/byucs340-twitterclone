package edu.byu.cs.tweeter.model.net;


import android.os.Build;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.RequiresApi;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * A temporary class that generates and returns Status objects. This class may be removed when the
 * server is created and the ServerFacade no longer needs to return dummy data.
 */
public class StatusGenerator {

    private static StatusGenerator statusGenerator;

    /**
     * A private constructor that ensures no instances of this class can be created from outside
     * the class.
     */
    private StatusGenerator() {}

    /**
     * Returns the singleton instance of the class
     *
     * @return the instance.
     */
    public static StatusGenerator getInstance() {
        if(statusGenerator == null) {
            statusGenerator = new StatusGenerator();
        }

        return statusGenerator;
    }

    public List<Status> generateUserStatuses(User user, int numStatuses) {
        List<Status> statuses = new ArrayList<>();
        for(int i = 0; i < numStatuses; i++) {
            Status newStatus;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                newStatus = new Status(generateRandomTweet(), user, generateRandomDate());
                statuses.add(newStatus);
            }
        }
        return statuses;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDateTime generateRandomDate() {
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();

        long LOWER_RANGE = 1; //assign lower range value
        long UPPER_RANGE = 100000000; //assign upper range value

        return now.minusSeconds(LOWER_RANGE + random.nextLong() * (UPPER_RANGE - LOWER_RANGE));
    }

    private String generateRandomTweet(){
        String[] randomTweets = {"This is a tweet" , "This is also a tweet.",
                "Go to this link: http://google.com", "Here: http://google.com,",
                "This is so cool.", "What's up @coolBob", "Not much", "I love tweeting"};
        Random generator = new Random();
        int randomIndex = generator.nextInt(randomTweets.length);
        return randomTweets[randomIndex];
    }

}
