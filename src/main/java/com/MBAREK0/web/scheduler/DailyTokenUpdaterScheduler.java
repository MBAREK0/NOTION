package com.MBAREK0.web.scheduler;

import com.MBAREK0.web.config.PersistenceManager;
import com.MBAREK0.web.entity.User;
import com.MBAREK0.web.service.UserService;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@Startup
@Singleton
public class DailyTokenUpdaterScheduler {



    private UserService userService; // Assuming you have a UserService to manage users

    public DailyTokenUpdaterScheduler() {
        EntityManager entityManager = PersistenceManager.getEntityManager();
        // Initialize the UserService
        this.userService = new UserService(entityManager);
    }

    @Schedule(hour = "0", minute = "15", persistent = false) // Runs every day at 00:15
    public void updateUserTokens() {
        // Get users with eligible_for_double_tokens > 0
        List<User> eligibleUsers = userService.getUsersWithEligibleDoubleTokens();

        for (User user : eligibleUsers) {
            int eligibleTokens = user.getEligibleForDoubleTokens();
            if (eligibleTokens > 0) {
                // Double the tokens
                int newTokenCount = user.getToken().getModifyTokenCount() * (2 * eligibleTokens);

                user.getToken().setModifyTokenCount(newTokenCount);

                user.setEligibleForDoubleTokens(0);

                userService.updateUser(user);
            }
        }
    }
}
