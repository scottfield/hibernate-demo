package com.jackie.hibernate.service;

import com.jackie.hibernate.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by jackie on 5/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/applicationcontext.xml"})
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setUsername("jackie");
        user.setPassword("password");
        assertNull(user.getId());
        userService.addUser(user);
        assertNotNull(user.getId());
        System.out.println(user.getId());
    }

    @Test
    public void testDeleteUsers() throws Exception {
        userService.deleteUsers();
    }

    @Test
    public void updateUser() throws Exception {
        User jackie = userService.findUserById(new Long(34));
//        User anotherjackie = userService.findUserById(new Long(34));
//        System.out.println("jackie==>"+jackie.hashCode());
//        System.out.println("anotherjackie==>"+anotherjackie.hashCode());
////        System.out.println(jackie.equals(anotherjackie));
//        System.out.println(jackie.getId()==anotherjackie.getId());
//        assertEquals(jackie,anotherjackie);
//        jackie.setUsername("jackies");
//        jackie.setPassword("passwordchange");
//        userService.updateUser(jackie);
    }

    @Test
    public void testBatchCreateUser() throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            users.add(user);
        }
        userService.batchCreateUser(users);
    }

    @Test
    public void testHqUpdateUser() throws Exception {
        User user = userService.findUserById(849683L);
        user.setUsername("tomcat");
        userService.hqUpdateUser(user);
    }

    @Test
    public void testFindUserByUsername() throws Exception {
        userService.findUserByUsername("jackie");
    }

    @Test
    public void testFindUserById() throws Exception {
        userService.findUserById(849683L);
        userService.findUserById(849683L);
        userService.findUserById(849683L);
        userService.findUserById(849683L);
    }
}