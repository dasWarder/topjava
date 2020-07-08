package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = Profiles.JPA)
public class JpaUserServiceTest extends UserServiceTest {

}
