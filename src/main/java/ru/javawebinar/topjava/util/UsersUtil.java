package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
        new User("user_1", "dev@null.ru", "qwerty", Role.ROLE_USER),
        new User( "user_2", "dev@null.ru", "qwerty", Role.ROLE_ADMIN)
    );
}
