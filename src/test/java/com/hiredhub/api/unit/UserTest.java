package com.hiredhub.api.unit;

import com.hiredhub.api.model.Application;
import com.hiredhub.api.model.Company;
import com.hiredhub.api.model.JobPosting;
import com.hiredhub.api.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @DisplayName("중복 지원_true")
    @Test
    void hasApplied() {
        List<Application> applications = new ArrayList<>();
        User user = new User(1L, "test", applications);
        JobPosting jobPosting = new JobPosting(1L, "test", "test", "test", 1_000_000, "test", "test", new Company(1L, "test"));

        applications.add(new Application(1L, user, jobPosting));

        assertThat(user.hasApplied(jobPosting)).isTrue();
    }

    @DisplayName("중복 지원_false")
    @Test
    void hasNotApplied() {
        List<Application> applications = new ArrayList<>();
        User user = new User(1L, "test", applications);
        JobPosting jobPosting = new JobPosting(1L, "test", "test", "test", 1_000_000, "test", "test", new Company(1L, "test"));

        assertThat(user.hasApplied(jobPosting)).isFalse();
    }
}
