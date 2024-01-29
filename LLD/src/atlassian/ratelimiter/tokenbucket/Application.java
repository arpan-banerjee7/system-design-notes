package lld.atlassian.ratelimiter.tokenbucket;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        UserBucketCreator userWithCredit = new UserBucketCreator(1, true);
        UserBucketCreator userWithoutCredit = new UserBucketCreator(2, false);

        Thread t1 = new Thread(() -> {
            simulateUserActivity(userWithoutCredit, "User without credit:");
        });

        Thread t2 = new Thread(() -> {
            simulateUserActivity(userWithCredit, "User with credit:");
        });

        t1.start();
        t2.start();
    }

    private static void simulateUserActivity(UserBucketCreator user, String userType) {
        int consumedRequests = 0;
        int totalRequests = 0;

        user.accessApplication();
        consumedRequests++;
        totalRequests++;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 11; i++) {
            if (user.accessApplication()) {
                consumedRequests++;
            }
            totalRequests++;
        }

        System.out.println(userType);
        System.out.println("  Total requests made: " + totalRequests);
        System.out.println("  Consumed requests: " + consumedRequests);
    }
}

