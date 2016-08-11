package com.insurance.models;

import com.insurance.enums.VehicleModel;
import com.insurance.enums.VehicleType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vehicles")
@Access(AccessType.PROPERTY)
public class Vehicle {
    private int id;
    private String vehicleName;
    private VehicleModel vehicleModel;
    private VehicleType vehicleType;
    private Date vehicleDate;
    private Client client;

    public Vehicle(){}

    public Vehicle(String vehicleName, VehicleModel vehicleModel, VehicleType vehicleType, Date vehicleDate) {
        this(vehicleName, vehicleModel, vehicleType, vehicleDate, null);
    }

    public Vehicle(String vehicleName, VehicleModel vehicleModel, VehicleType vehicleType, Date vehicleDate, Client client) {
        this.vehicleName = vehicleName;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.vehicleDate = vehicleDate;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "vehicleName", nullable = false, length = 45)
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    @Basic
    @Column(name = "vehicleModel", nullable = false)
    @Enumerated(EnumType.STRING)
    public VehicleModel getVehicleModel() {
        return vehicleModel;
    }
    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    @Basic
    @Column(name = "vehicleType", nullable = false)
    @Enumerated(EnumType.STRING)
    public VehicleType getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "vehicleDate")
    public Date getVehicleDate() {
        return vehicleDate;
    }
    public void setVehicleDate(Date vehicleDate) {
        this.vehicleDate = vehicleDate;
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
