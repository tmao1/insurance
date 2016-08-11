package com.insurance.models;

/**
 * Created by localadmin on 8/11/16.
 */
import javax.persistence.*;
import com.insurance.enums.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Access(AccessType.PROPERTY)
public class Client {
    private int id;
    private String name;
    private Gender gender;
    private int age;
    private List<Vehicle> vehicles;

    public Client(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.vehicles = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    @Basic
    @Column(name = "age", nullable = false, length = 3)
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}