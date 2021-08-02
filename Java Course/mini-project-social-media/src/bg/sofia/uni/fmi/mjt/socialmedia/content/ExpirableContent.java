package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.time.LocalDateTime;

public record ExpirableContent(Content content, LocalDateTime dateOfPublishing, int hoursTillExpiration) {
    public boolean isExpired() {
        return dateOfPublishing.plusHours(hoursTillExpiration).isBefore(LocalDateTime.now());
    }
}
