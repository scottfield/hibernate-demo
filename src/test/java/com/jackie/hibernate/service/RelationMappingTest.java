package com.jackie.hibernate.service;

import com.jackie.hibernate.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:/applicationcontext.xml"})
@Transactional(readOnly = false)
public class RelationMappingTest {
    @Autowired
    private HibernateTemplate template;

    @Test
    public void testManyToOne() throws Exception {
        Person person = new Person();
        template.persist(person);

        Phone phone = new Phone("123-456-7890");
        phone.setPerson(person);
        template.persist(phone);

        template.flush();
        phone.setPerson(null);
    }

    @Test
    public void testOneToMany() throws Exception {
        Person person = new Person();
        Phone phone1 = new Phone("123-456-7890");
        Phone phone2 = new Phone("321-654-0987");

        person.addPhone(phone2);
        person.addPhone(phone1);

        template.save(person);
        template.flush();
        template.refresh(person);
        System.out.println(person);
//        person.removePhone(phone1);
//        person.removePhone(phone2);
//        template.flush();

    }

    @Test
    public void testOneToOne() throws Exception {
        Phone phone = new Phone("123-456-7890");
        PhoneDetails details = new PhoneDetails("T-Mobile", "GSM");

        phone.addPhoneDetail(details);
        template.save(phone);
        PhoneDetails otherDetails = new PhoneDetails("T-Mobile", "CDMA");
        otherDetails.setPhone(phone);
        template.save(otherDetails);
        template.flush();
        template.clear();
        phone = template.get(Phone.class, phone.getId());
    }

    @Test
    public void testManyToMany() throws Exception {
        Person person1 = new Person();
        Person person2 = new Person();

        Address address1 = new Address("12th Avenue", "12A");
        Address address2 = new Address("18th Avenue", "18B");

        person1.addAddress(address1);
        person1.addAddress(address2);

        person2.addAddress(address1);
        person2.addAddress(address2);
        template.persist(person1);
        template.persist(person2);

        template.flush();

        person1.getAddresses().remove(address1);
        template.flush();
        template.delete(person1);
        template.flush();
        person2.removeAddress(address2);
        template.flush();

    }

    @Test
    public void testNotFound() throws Exception {
        City _NewYork = new City();
        _NewYork.setName("New York");
        template.persist(_NewYork);
        Person person = new Person();
        person.setName("John Doe");
        person.setCityName("New York");
        template.persist(person);
        template.clear();
        template.refresh(person);
        assertNotNull(person);
        assertEquals("New York", person.getCityName());
        assertNotNull(person.getCity());
    }
}
