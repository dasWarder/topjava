package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private AtomicInteger id = new AtomicInteger(0);
    private Map<Integer, User> listOfUsers = new ConcurrentHashMap<>();

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        try {
            listOfUsers.remove(id);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);

        if(user.isNew()) {
            user.setId(id.getAndIncrement());
            listOfUsers.put(user.getId(), user);
            return user;
        }
        return listOfUsers.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        User res = null;
        if ((res = listOfUsers.get(id)) != null) {
            return res;
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return listOfUsers.values()
                .stream()
                .sorted(Comparator.comparing(User::getName)
                .thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return getAll().stream()
                .filter(usert-> usert.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
