package com.insurance.models;


import com.insurance.enums.*;
import com.insurance.util.Mysql;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import static org.junit.Assert.*;

public class VehicleTest {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
//        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createNativeQuery("truncate table vehicles").executeUpdate();
 //       session.createNativeQuery("set FOREIGN_KEY_CHECKS = 1").executeUpdate();
        Vehicle vehicle = new Vehicle("RAV 4", VehicleModel.TOYOTA, VehicleType.SUV, format.parse("2000-01-03"));
        session.save(vehicle);
        session.getTransaction().commit();
        session.close();
    }

    @After
    public void tearDown() throws Exception {
    }

    // **************
    // *** CREATE ***
    // **************

    @Test
    public void shouldCreateNewAnimalAndSave() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Vehicle vehicle = new Vehicle("CIVIC", VehicleModel.HONDA, VehicleType.SUV, format.parse("2000-01-03"));
        session.save(vehicle);
        session.getTransaction().commit();
        session.close();
        assertEquals(2, vehicle.getId());
    }

    @Test(expected = org.hibernate.PropertyValueException.class)
    public void shouldNotSaveDueToNullName() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Vehicle vehicle = new Vehicle();

        try {
            session.save(vehicle);
            session.getTransaction().commit();
        }finally {
            session.close();
        }
    }

    // **************
    // *** READ ***
    // **************

    @Test
    public void shouldGetExistingVehicle() throws Exception {
        Session session = Mysql.getSession();
        Vehicle vehicle = session.get(Vehicle.class, 1);
        session.close();
        assertEquals(1, vehicle.getId());
        assertEquals("RAV 4", vehicle.getVehicleName());
        assertEquals(VehicleModel.TOYOTA, vehicle.getVehicleModel());
        assertEquals(VehicleType.SUV, vehicle.getVehicleType());

    }


    // **************
    // *** UPDATE ***
    // **************

    @Test
    public void shouldUpdateExistingVehicle() throws Exception {
        Session session = Mysql.getSession();

        session.beginTransaction();
        Vehicle v1 = session.get(Vehicle.class, 1);
        v1.setVehicleName("ACCORD");
        session.getTransaction().commit();

        session.refresh(v1);
        session.close();

        assertEquals(1, v1.getId());
        assertEquals("ACCORD", v1.getVehicleName());
        assertEquals(VehicleModel.TOYOTA, v1.getVehicleModel());
    }

    // **************
    // *** DELETE ***
    // **************

    @Test
    public void shouldDeleteExistingVehicle() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Vehicle vehicle = new Vehicle("RAV 4", VehicleModel.TOYOTA, VehicleType.SUV, format.parse("2000-01-03"));
        //Vehicle vehicle = new Vehicle("CIVIC", VehicleModel.HONDA, VehicleType.SUV, format.parse("2003-01-03"));
        vehicle.setId(1);
        session.delete(vehicle);
        session.getTransaction().commit();
        session.close();


    }

}

