//package com.cts.repository;
//
//import com.cts.model.User;
//import com.cts.model.User.Role;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository repo;
//
//    @Test
//    void testSaveUser() {
//        User u = new User();
//        u.setName("Archana");
//        u.setEmail("archana@test.com");
//        u.setPhone("88888");
//        u.setPassword("pwd");
//        u.setRole(Role.Viewer);
//
//        User saved = repo.save(u);
//
//        assertNotNull(saved.getUserId());
//        assertEquals("Archana", saved.getName());
//    }
//
//    @Test
//    void testFindByEmail() {
//        User u = new User();
//        u.setName("Test");
//        u.setEmail("user@test.com");
//        u.setPhone("12345");
//        u.setPassword("pwd");
//        u.setRole(Role.Editor);
//        repo.save(u);
//
//        User found = repo.findByEmail("user@test.com").orElse(null);
//
//        assertNotNull(found);
//        assertEquals("user@test.com", found.getEmail());
//    }
//}