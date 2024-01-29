package lld.atlassian.ratelimiter.tokenbucket;

public class TokenBucketConstants {
    public static int numberOfRequest = 5;

    public static int windowSizeForRateLimitInMilliSeconds = 1 * 1000;

    public static int maxBucketSize = 10;
}
