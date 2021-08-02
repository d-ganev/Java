package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.ExpirableContent;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.NoUsersException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;
import java.util.Collection;

public class EvilSocialInatorValidator {

    private boolean isContentPublished(String id, Collection<User> registeredUsers) {
        for (User currentUser : registeredUsers) {
            for (ExpirableContent expContent : currentUser.getOwnSocialContent()) {
                String currentId = expContent.content().getId();
                if (currentId.equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isUserRegistered(String username, Collection<User> registeredUsers) {
        for (User currentUser : registeredUsers) {
            String currentUsername = currentUser.getUsername();
            if (currentUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void validateRegisterMethod(String username, Collection<User> registeredUsers)
            throws UsernameAlreadyExistsException {
        if (username == null) {
            throw new IllegalArgumentException("The input is not valid!");
        }
        if (isUserRegistered(username, registeredUsers)) {
            throw new UsernameAlreadyExistsException(String.format("The user: %s is already registered", username));
        }
    }


    public void validatePublishContentMethod(String username, LocalDateTime publishedOn,
                                             String description, Collection<User> registeredUsers) {
        if (username == null || publishedOn == null || description == null) {
            throw new IllegalArgumentException("The input is not valid!");
        }
        if (!isUserRegistered(username, registeredUsers)) {
            throw new UsernameNotFoundException(String.format("The user: %s is not yet registered", username));
        }
    }

    public void validateLikeMethod(String username, String id, Collection<User> registeredUsers) {
        if (username == null || id == null) {
            throw new IllegalArgumentException("The input is not valid");
        }

        if (!isUserRegistered(username, registeredUsers)) {
            throw new UsernameNotFoundException(String.format("The user: %s is not yet registered", username));
        }

        if (!isContentPublished(id, registeredUsers)) {
            throw new ContentNotFoundException(String.format("Content with id %s is not yet published", id));
        }
    }


    public void validateCommentMethod(String username, String text, String id, Collection<User> registeredUsers) {
        if (username == null || text == null || id == null) {
            throw new IllegalArgumentException("The input is not valid");
        }

        if (!isUserRegistered(username, registeredUsers)) {
            throw new UsernameNotFoundException(String.format("The user %s is not yet registered", username));
        }

        if (!isContentPublished(id, registeredUsers)) {
            throw new ContentNotFoundException(String.format("Content with id %s is not yet published", id));
        }
    }


    public void validateGetNMostPopularContentMethod(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The input is a negative number!");
        }
    }


    public void validateGetNMostRecentContentMethod(String username, int n, Collection<User> registeredUsers) {
        if (n < 0 || username == null) {
            throw new IllegalArgumentException("The input is not valid!");
        }
        if (!isUserRegistered(username, registeredUsers)) {
            throw new UsernameNotFoundException(String.format("The user: %s is not yet registered", username));
        }
    }


    public void validateGetMostPopularUserMethod(Collection<User> registeredUsers) {
        if (registeredUsers.isEmpty()) {
            throw new NoUsersException("There are no users in the platform");
        }
    }


    public void validateFindContentByTagMethod(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException("The input is not valid");
        }
    }


    public void validateGetActivityLogMethod(String username, Collection<User> registeredUsers) {
        if (username == null) {
            throw new IllegalArgumentException("The input is not valid!");
        }
        if (!isUserRegistered(username, registeredUsers)) {
            throw new UsernameNotFoundException(String.format("The user: %s is not yet registered", username));
        }
    }

}
