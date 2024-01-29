package lld.atlassian.ratelimiter.tokenbucket;

public class TokenBucket {
    private int numberOfTokenAvailable;
    private int numberOfRequests;
    private int windowSizeForRateLimitInMilliSeconds;
    private long lastRefillTime;
    private long nextRefillTime;
    private int maxBucketSize;
    private boolean applyCreditRequests;

    public TokenBucket(int maxBucketSize, int numberOfRequests, int windowSizeForRateLimitInMilliSeconds, boolean applyCreditRequests) {
        this.maxBucketSize = maxBucketSize;
        this.numberOfRequests = numberOfRequests;
        this.windowSizeForRateLimitInMilliSeconds = windowSizeForRateLimitInMilliSeconds;
        this.applyCreditRequests = applyCreditRequests;
        this.refill();
    }

    public boolean tryConsume() {
        refill();
        if (this.numberOfTokenAvailable > 0) {
            this.numberOfTokenAvailable--;
            return true;
        }
        return false;
    }

    private void refill() {
        if (System.currentTimeMillis() < this.nextRefillTime) {
            return;
        }
        this.lastRefillTime = System.currentTimeMillis();
        this.nextRefillTime = this.lastRefillTime + this.windowSizeForRateLimitInMilliSeconds;
        if (applyCreditRequests) {
            int creditRequest = this.numberOfTokenAvailable + this.numberOfRequests;
            this.numberOfTokenAvailable = Math.min(this.maxBucketSize, creditRequest);
        } else {
            this.numberOfTokenAvailable = this.numberOfRequests;
        }
    }
}




