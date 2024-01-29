package lld.atlassian.ratelimiter.tokenbucket;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserBucketCreator {
    private int userId;
    int maxCredits;
    Map<Integer, TokenBucket> bucket;

    public UserBucketCreator(int id, boolean applyCreditRequests) {
        this.bucket = new HashMap<>();
        this.userId = id;
        bucket.put(id, new TokenBucket(TokenBucketConstants.maxBucketSize,
                TokenBucketConstants.numberOfRequest,
                TokenBucketConstants.windowSizeForRateLimitInMilliSeconds, applyCreditRequests));
    }

    boolean accessApplication() {
        return bucket.get(userId).tryConsume();
    }
}

