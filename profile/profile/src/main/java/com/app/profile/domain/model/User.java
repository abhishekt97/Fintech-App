package com.app.profile.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class User {

    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String occupation;
    private int age;
    private transient LocalDate registerdSince; // marked transient b/c you use Gson by accident to access internal fields of third-party classes and there are 2 ways to solve 1. remove fields you don't want to deserealize and 2. Write custom Gson TypeAdapter implementations or the affected classes.

    public User(){}

    public User(String username, String firstname, String lastname, String occupation, int age, LocalDate registerdSince) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.occupation = occupation;
        this.age = age;
        this.registerdSince = registerdSince;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getRegisterdSince() {
        return registerdSince;
    }

    public void setRegisterdSince(LocalDate registerdSince) {
        this.registerdSince = registerdSince;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", occupation='" + occupation + '\'' +
                ", age=" + age +
                ", registerdSince=" + registerdSince +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(username, user.username) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(occupation, user.occupation) && Objects.equals(registerdSince, user.registerdSince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstname, lastname, occupation, age, registerdSince);
    }
}
