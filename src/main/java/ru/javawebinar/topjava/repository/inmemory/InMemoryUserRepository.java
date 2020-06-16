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
        return null;
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
        List<User> listOf = new ArrayList<>();

        for (Map.Entry<Integer, User> map : listOfUsers.entrySet()) {
            listOf.add(map.getValue());
        }

        Collections.sort(listOf, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return listOf;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        User resUser= null;
        for(Map.Entry<Integer, User> map : listOfUsers.entrySet()) {
            if ((resUser = map.getValue()).getEmail().equals(email)) {
                return resUser;
            }
        }
        return null;
    }
}
