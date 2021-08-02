package bg.sofia.uni.fmi.mjt.netflix.platform;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.content.StreamableContent;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.UserNotFoundException;

public class Netflix implements StreamingService {
    private Account[] accounts;
    private Streamable[] streamableContent;


    public Netflix(Account[] accounts, Streamable[] streamableContent) {
        this.accounts = accounts;
        this.streamableContent = streamableContent;
    }

    public void checkForRegistration(Account user) {
        for (Account currUser : accounts) {
            String currUserName = currUser.username();
            String userName = user.username();
            if (currUserName.equals(userName)) {
                return;
            }
        }
        throw new UserNotFoundException("The user is not registered!");
    }

    public void checkForContentAvailability(String videoContentName) {
        Streamable content = findByName(videoContentName);
        if (content == null) {
            throw new ContentNotFoundException("The content is not contained in Netflix!");
        }
    }

    public void compare(PgRating rating, int age) throws ContentUnavailableException {
        if (rating.equals(PgRating.NC17) && age < 18) {
            throw new ContentUnavailableException
                    ("The content is unavailable because it is for users over 17 years old!");
        } else if (rating.equals(PgRating.PG13) && age < 14) {
            throw new ContentUnavailableException
                    ("The content is unavailable because it is for users over 13 years old!");
        }
    }

    public void checkForAgeRestriction(Account user, String videoContentName) throws ContentUnavailableException {
        int userAge = user.getAge();
        Streamable content = findByName(videoContentName);

        try {
            compare(content.getRating(), userAge);
        } catch (ContentUnavailableException exception) {
            throw exception;
        }
    }

    @Override
    public void watch(Account user, String videoContentName) throws ContentUnavailableException {
        try {
            checkForRegistration(user);
            checkForContentAvailability(videoContentName);
            checkForAgeRestriction(user, videoContentName);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("The user is not registered!");
        } catch (ContentNotFoundException e) {
            throw new ContentNotFoundException("The content is not consisted in Netflix");
        } catch (ContentUnavailableException e) {
            throw new ContentUnavailableException("The content is unavailable for the user due to age restriction!");
        }

        StreamableContent content = (StreamableContent) findByName(videoContentName);    // cast
        content.increaseWatches();
    }

    @Override
    public Streamable findByName(String videoContentName) {
        for (Streamable current : streamableContent) {
            String currName = current.getTitle();
            if (currName.equals(videoContentName)) {
                return current;
            }
        }
        return null;
    }

    @Override
    public Streamable mostViewed() {
        if (this.streamableContent == null) {
            return null;
        }
        int maxWatches = ((StreamableContent) streamableContent[0]).getNumberOfWatches();
        int currWatches;
        Streamable mostViewed = streamableContent[0];
        for (int i = 1; i < streamableContent.length; i++) {
            currWatches = ((StreamableContent) streamableContent[i]).getNumberOfWatches();
            if (currWatches > maxWatches) {
                maxWatches = currWatches;
                mostViewed = streamableContent[i];
            }
        }
        if (maxWatches == 0) {
            return null;
        } else {
            return mostViewed;
        }
    }

    @Override
    public int totalWatchedTimeByUsers() {
        int totalWatchedTime = 0;
        for (int i = 0; i < streamableContent.length; i++) {
            int currNumberOfWatches = ((StreamableContent) streamableContent[i]).getNumberOfWatches();
            int currDuration = streamableContent[i].getDuration();
            totalWatchedTime += currNumberOfWatches * currDuration;
        }
        return totalWatchedTime;
    }
}
