package nao.fit.bstu.lab3.Room;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Cybersport implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private int age;
    private double salary;
    private String phoneNumber;
    private String photo;
    private String email;
    private String instagram;

    public Cybersport(int id, String name, String lastName, int age, double salary, String phoneNumber, String email, String instagram) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.instagram = instagram;
    }

    public Cybersport(int id, String name, String lastName, int age, double salary, String phoneNumber, String email, String instagram, String photo) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.instagram = instagram;
        this.photo=photo;
    }

    public Cybersport() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) { this.instagram = instagram;}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
