package User;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGeneration {

    public static User createRandomUser() {
        return new User(
                RandomStringUtils.randomAlphanumeric(5) + "@gmail.com", RandomStringUtils.randomAlphanumeric(7), RandomStringUtils.randomAlphanumeric(15));
    }

    public static User createRandomUserWithoutName() {
        return new User(RandomStringUtils.randomAlphanumeric(5) + "@gmail.com", null, RandomStringUtils.randomAlphanumeric(15));
    }
}
