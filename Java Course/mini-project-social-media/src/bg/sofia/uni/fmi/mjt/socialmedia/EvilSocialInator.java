package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.BaseContent;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.content.ExpirableContent;
import bg.sofia.uni.fmi.mjt.socialmedia.content.PopularContentComparator;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;
import bg.sofia.uni.fmi.mjt.socialmedia.content.RecentContentComparator;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Story;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvilSocialInator implements SocialMediaInator {
    private final HashSet<User> registeredUsers;
    private int numberOfContents;
    private final EvilSocialInatorValidator validator;

    public EvilSocialInator() {
        registeredUsers = new HashSet<>();
        validator = new EvilSocialInatorValidator();
        numberOfContents = 0;
    }

    private void increaseContentsNumber() {
        numberOfContents++;
    }

    private void analiseDescription(Content content, String description) {
        String[] wordsFromDescription = description.split(" ");
        for (String word : wordsFromDescription) {
            if (word.startsWith("#")) {
                content.getTags().add(word);
            } else if (word.startsWith("@")) {
                int usernameStartIndex = 1;
                String username = word.substring(usernameStartIndex);
                if (validator.isUserRegistered(username, getRegisteredUsers())) {  //add username to mentions in content
                    content.getMentions().add(word);
                    User mentionedUser = findRegisteredUserByName(username);      // increase number of user mentions
                    mentionedUser.increaseMentions();
                }
            }
        }
    }

    private User findRegisteredUserByName(String username) {
        User neededUser = null;
        for (User currentUser : registeredUsers) {
            String currentUsername = currentUser.getUsername();
            if (currentUsername.equals(username)) {
                neededUser = currentUser;
                break;
            }
        }
        return neededUser;
    }

    private Content findContentById(String id) {
        Content neededContent = null;
        for (User currentUser : registeredUsers) {
            for (ExpirableContent expContent : currentUser.getOwnSocialContent()) {
                String currentId = expContent.content().getId();
                if (currentId.equals(id)) {
                    neededContent = expContent.content();
                    break;
                }
            }
        }
        return neededContent;
    }

    private void addContentToUserAccount(String username, LocalDateTime publishedOn,
                                         String description, String contentType, String id) {
        Content contentToBePublished = null;
        int hoursTillExpiration = 0;

        if ("post".equals(contentType)) {
            contentToBePublished = new Post(id);
            hoursTillExpiration = Post.getPostDuration();
        } else if ("story".equals(contentType)) {
            contentToBePublished = new Story(id);
            hoursTillExpiration = Story.getStoryDuration();
        }

        analiseDescription(contentToBePublished, description);

        ExpirableContent expContentToBePublished
                = new ExpirableContent(contentToBePublished, publishedOn, hoursTillExpiration);

        User contentCreator = findRegisteredUserByName(username);
        contentCreator.addContent(expContentToBePublished);
    }

    private String createId(String username) {
        String id = username + '-' + String.valueOf(numberOfContents);
        increaseContentsNumber();
        return id;
    }

    private Set<Content> getNonExpiredContent() {
        Set<Content> availableContent = new HashSet<>();
        for (User currUser : registeredUsers) {                // removing the expired content
            for (ExpirableContent expContent : currUser.getOwnSocialContent()) {
                if (!expContent.isExpired()) {
                    availableContent.add(expContent.content());
                }
            }
        }
        return availableContent;
    }

    private Set<ExpirableContent> getNonExpiredContentOfUser(User neededUser) {
        Set<ExpirableContent> availableContent = new HashSet<>();
        for (ExpirableContent expContent : neededUser.getOwnSocialContent()) {
            if (!expContent.isExpired()) {
                availableContent.add(expContent);
            }
        }
        return availableContent;
    }

    public Collection<User> getRegisteredUsers() {
        return this.registeredUsers;
    }

    @Override
    public void register(String username) throws UsernameAlreadyExistsException {
        validator.validateRegisterMethod(username, getRegisteredUsers());

        User userToBeRegistered = new User(username);
        registeredUsers.add(userToBeRegistered);
    }

    @Override
    public String publishPost(String username, LocalDateTime publishedOn, String description) {
        validator.validatePublishContentMethod(username, publishedOn, description, getRegisteredUsers());

        String postId = createId(username);
        addContentToUserAccount(username, publishedOn, description, "post", postId);

        ActivityLog activityLogOfCurrentUser = findRegisteredUserByName(username).getActivityLog();
        activityLogOfCurrentUser.addPostPublishingActivity(publishedOn, postId);

        return postId;
    }

    @Override
    public String publishStory(String username, LocalDateTime publishedOn, String description) {
        validator.validatePublishContentMethod(username, publishedOn, description, getRegisteredUsers());

        String storyId = createId(username);
        addContentToUserAccount(username, publishedOn, description, "story", storyId);

        ActivityLog activityLogOfCurrentUser = findRegisteredUserByName(username).getActivityLog();
        activityLogOfCurrentUser.addStoryPublishingActivity(publishedOn, storyId);

        return storyId;
    }

    @Override
    public void like(String username, String id) {
        validator.validateLikeMethod(username, id, getRegisteredUsers());

        BaseContent contentToBeLiked = (BaseContent) findContentById(id);
        contentToBeLiked.increaseLikes();

        ActivityLog activityLogOfCurrentUser = findRegisteredUserByName(username).getActivityLog();
        activityLogOfCurrentUser.addContentLikingActivity(LocalDateTime.now(), id);
    }

    @Override
    public void comment(String username, String text, String id) {
        validator.validateCommentMethod(username, text, id, getRegisteredUsers());

        BaseContent contentToBeLiked = (BaseContent) findContentById(id);
        contentToBeLiked.increaseComments();

        ActivityLog activityLogOfCurrentUser = findRegisteredUserByName(username).getActivityLog();
        activityLogOfCurrentUser.addContentCommentingActivity(LocalDateTime.now(), text, id);
    }

    @Override
    public Collection<Content> getNMostPopularContent(int n) {
        validator.validateGetNMostPopularContentMethod(n);

        Set<Content> availableContent = getNonExpiredContent();
        List<Content> sortedContent = new ArrayList<>(availableContent);
        sortedContent.sort(new PopularContentComparator());
        if (n > sortedContent.size()) {                              // return all available
            return Collections.unmodifiableCollection(sortedContent);
        }

        List<Content> mostPopularContent = sortedContent.subList(0, n);
        return Collections.unmodifiableCollection(mostPopularContent);
    }

    @Override
    public Collection<Content> getNMostRecentContent(String username, int n) {
        validator.validateGetNMostRecentContentMethod(username, n, getRegisteredUsers());

        User neededUser = findRegisteredUserByName(username);
        Set<ExpirableContent> availableContent = getNonExpiredContentOfUser(neededUser);
        List<ExpirableContent> sortedExpirableContent = new ArrayList<>(availableContent);
        sortedExpirableContent.sort(new RecentContentComparator());
        List<Content> sortedContent = new ArrayList<>();      // Collection<Content> must be returned

        for (ExpirableContent expContent : sortedExpirableContent) {
            sortedContent.add(expContent.content());
        }

        if (n > sortedContent.size()) {                      // return all available
            return Collections.unmodifiableCollection(sortedContent);
        }
        List<Content> mostPopularContent = sortedContent.subList(0, n);
        return Collections.unmodifiableCollection(mostPopularContent);
    }

    @Override
    public String getMostPopularUser() {
        validator.validateGetMostPopularUserMethod(getRegisteredUsers());

        int numberOfMostMentions = 0;
        String usernameOfMostPopularUser = null;
        for (User currentUser : registeredUsers) {
            int numberOfCurrentMentions = currentUser.getNumberOfMentions();
            if (numberOfCurrentMentions > numberOfMostMentions) {
                numberOfMostMentions = numberOfCurrentMentions;
                usernameOfMostPopularUser = currentUser.getUsername();
            }
        }
        if (numberOfMostMentions != 0) {
            return usernameOfMostPopularUser;
        } else {
            return registeredUsers.iterator().next().getUsername();       // no matter who if no one is mentioned
        }
    }

    @Override
    public Collection<Content> findContentByTag(String tag) {
        validator.validateFindContentByTagMethod(tag);

        Set<Content> contentWithSearchedTag = new HashSet<>();
        for (User currUser : registeredUsers) {
            for (ExpirableContent expContent : currUser.getOwnSocialContent()) {
                Collection<String> currContentTags = expContent.content().getTags();
                if (!expContent.isExpired() && currContentTags.contains(tag)) {
                    contentWithSearchedTag.add(expContent.content());
                }
            }
        }
        return Collections.unmodifiableCollection(contentWithSearchedTag);
    }

    @Override
    public List<String> getActivityLog(String username) {
        validator.validateGetActivityLogMethod(username, getRegisteredUsers());

        Collection<String> activities = findRegisteredUserByName(username).getActivityLog().getActivities().values();
        List<String> activityLog = new ArrayList<>(activities);
        Collections.reverse(activityLog);                     // should be in reversed order
        return activityLog;
    }
}
