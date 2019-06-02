package com.example.ec.repo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Mary Ellen Bowman.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void testFindByUsername() {
//        Optional<User> user = repository.findByUsername("admin");
//        assertTrue(user.isPresent());

//        user = repository.findByUsername("nobody");
//        assertFalse(user.isPresent());
    }
}