package com.nurture.lld.repositories;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class UserRepository {
    private Set<String> userSet = new TreeSet<>();

    public void addUserIfNotPresent(String userId) {
        userSet.add(userId);
    }

    public Set<String> getAllUsers() {
        return userSet.stream().collect(Collectors.toSet());
    }
}
