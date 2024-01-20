package com.example.hospital.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    Long id;
    String appointmentDate;
    String appointmentTime;
    String details;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    // @JsonIgnore
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    // @JsonIgnore
    Doctor doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override // Override the toString() method to return a custom string showing the
              // appointment details instead of the object's hashcode value
    public String toString() {
        return "Appointment : " +
                "ID : " + id + " | " +
                "AppointmentDate : " + appointmentDate + " | " +
                "AppointmentTime : " + appointmentTime + " | " +
                "Details : " + details;
    }

}
