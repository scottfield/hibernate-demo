package com.jackie.hibernate.model;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.omg.CORBA.PolicyError;
import org.springframework.scheduling.support.SimpleTriggerContext;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Person")
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "order_id")
    private List<Phone> phones = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "person_address",
            joinColumns =
            @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "address_id", referencedColumnName = "id")
    )
    private List<Address> addresses = new ArrayList<>();
    @Column(name = "city_name")
    private String cityName;
    @ManyToOne
    @JoinColumn(name = "city_name", referencedColumnName = "name", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private City city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setPerson(this);
    }

    public void removePhone(Phone phone) {
        phones.remove(phone);
        phone.setPerson(null);
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.getOwners().add(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.getOwners().remove(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
