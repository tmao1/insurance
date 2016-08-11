package com.insurance.models;

/**
 * Created by localadmin on 8/11/16.
 */

import com.insurance.enums.Gender;
import com.insurance.enums.VehicleModel;
import com.insurance.enums.VehicleType;
import com.insurance.util.Mysql;
import com.sun.tools.javac.jvm.Gen;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.Assert.*;

public class ClientTest {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Before
    public void setUp() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 0").executeUpdate();
        session.createNativeQuery("truncate table clients").executeUpdate();
        session.createNativeQuery("truncate table vehicles").executeUpdate();
        session.createNativeQuery("set FOREIGN_KEY_CHECKS = 1").executeUpdate();
        Client client = new Client("Jennifer Aniston", 32, Gender.F);
        Vehicle v1 = new Vehicle("RAV 4", VehicleModel.TOYOTA, VehicleType.SUV, format.parse("2001-01-03"));
        Vehicle v2 = new Vehicle("ACCORD", VehicleModel.HONDA, VehicleType.PASSENGER, format.parse("2001-03-03"));
        Vehicle v3 = new Vehicle("C 300", VehicleModel.MERCEDES, VehicleType.LUXURY, format.parse("2001-05-03"));

        client.getVehicles().add(v1);
        client.getVehicles().add(v2);
        client.getVehicles().add(v3);

        session.save(client);
        session.getTransaction().commit();
        session.close();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldCreateNewClientAndSave() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Client client = new Client("Jennifer Aniston", 32, Gender.F);
        session.save(client);
        session.getTransaction().commit();
        session.close();
        assertEquals(2, client.getId());
    }

    @Test(expected = org.hibernate.exception.DataException.class)
    public void shouldNotSaveDueToNameTooLong() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();
        Client client = new Client("01234567890123456789012345678901234567890123456789", 31, Gender.F);

        try {
            session.save(client);
            session.getTransaction().commit();
        }finally {
            session.close();
        }
    }


    // ************************
    // *** PROOF OF CONCEPT ***
    // ************************

    @Test
    public void notATestProofOfConcept() throws Exception {
        Session session = Mysql.getSession();
        session.beginTransaction();

        //Vehicle vehicle = session.get(Vehicle.class, 1);
        Client client = session.get(Client.class, 1);
        client.setName("John Bob Silver");
        Optional<Vehicle> car1 = client.getVehicles().stream().filter(a -> a.getVehicleName().equals("RAV 4")).findFirst();
        car1.ifPresent(f -> {
            f.setVehicleName("Another RAV");
        });

//        Optional<Animal> molly = shelter.getAnimals().stream().filter(a -> a.getName().equals("Molly")).findFirst();
//        molly.ifPresent(m -> {
//            shelter.getAnimals().remove(m);
//            session.delete(m);
//        });
//
//        Animal a4 = new Animal("Mildred", format.parse("2000-01-03"), Sex.F, "Dog", format.parse("2013-12-11"), shelter);
//        shelter.getAnimals().add(a4);

        session.getTransaction().commit();
        session.close();
    }
}

