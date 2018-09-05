package com.jackie.hibernate.dao.impl;

import com.jackie.hibernate.dao.UserDao;
import com.jackie.hibernate.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jackie on 5/13/2016.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Resource
    private HibernateTemplate template;

    @Override
    public void addUser(User user) {
        template.persist(user);
    }

    @Override
    public User updateUser(User user) {
        return template.merge(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return template.execute(session -> {
            Query query = session.createQuery("select user from User user where user.username=:username");
            query.setParameter("username", username);
            query.setCacheable(true);
            return (User) query.uniqueResult();
        });
    }

    @Override
    public User findUserById(Long id) {
        SessionFactory sessionFactory = template.getSessionFactory();
        Statistics statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);
//        System.out.println("Fetch Count=:"+statistics.getEntityFetchCount()+",Second Level Hit Count="+statistics.getSecondLevelCacheHitCount()+",Second Level Miss Count="+statistics.getSecondLevelCacheMissCount()+",Second Level Put Count="+statistics.getSecondLevelCachePutCount());

        User user = template.get(User.class, id);
        System.out.println(user);
        System.out.println("Fetch Count=:"+statistics.getEntityFetchCount()+",Second Level Hit Count="+statistics.getSecondLevelCacheHitCount()+",Second Level Miss Count="+statistics.getSecondLevelCacheMissCount()+",Second Level Put Count="+statistics.getSecondLevelCachePutCount());
        System.out.println(statistics);
//        user = template.get(User.class, id);
//        System.out.println(user);
//        System.out.println("Fetch Count=:"+statistics.getEntityFetchCount()+",Second Level Hit Count="+statistics.getSecondLevelCacheHitCount()+",Second Level Miss Count="+statistics.getSecondLevelCacheMissCount()+",Second Level Put Count="+statistics.getSecondLevelCachePutCount());
//        template.evict(user);
//        template.get(User.class, id);
//        System.out.println(user);
//        System.out.println("Fetch Count=:"+statistics.getEntityFetchCount()+",Second Level Hit Count="+statistics.getSecondLevelCacheHitCount()+",Second Level Miss Count="+statistics.getSecondLevelCacheMissCount()+",Second Level Put Count="+statistics.getSecondLevelCachePutCount());
        return user;
    }

    @Override
    public void deleteUsers() {
        template.execute(session -> {
            Query query = session.createQuery("delete from com.jackie.hibernate.model.User user");
            query.executeUpdate();
            return null;
        });
    }

    @Override
    public void batchCreateUser(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            template.persist(users.get(i));
            /*if (i % 20 == 0) {
                template.flush();
                template.clear();
            }*/
        }
    }

    @Override
    public void hqUpdateUser(User user) {
        template.execute(session -> {
            Query query = session.createQuery("update versioned User user set user.username=:username");
            query.setParameter("username", user.getUsername());
            query.executeUpdate();
            return null;
        });
    }
}
