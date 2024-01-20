package com.example.hospital.Controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.hospital.Entity.Appointment;
import com.example.hospital.Entity.Doctor;
import com.example.hospital.Entity.Patient;
import com.example.hospital.Repository.AppointmentRepository;
import com.example.hospital.Repository.PatientRepository;

@RestController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    PatientRepository patRep;

    @Autowired
    AppointmentRepository appRep;

    // Get all patients name only but without appointments and doctors information
    @GetMapping
    public String getAll() {
        String result = "";

        for (Patient p : patRep.findAll()) {
            result += "Name : " + p.getName() + " | " + "Gender: " + p.getGender() + " | " + "Date of Birth: "
                    + p.getDob() + " | " + p.getAppointments();

            for (Appointment a : p.getAppointments()) {
                Doctor d = a.getDoctor();
                result += " | " + d.getName();
            }

            result += "<br>";
        }

        return result;
    }

    // Get a single patient by id
    @GetMapping("/{id}")
    public String getPatientById(@PathVariable Long id) {
        String result = "";

        Optional<Patient> optionalPatient = patRep.findById(id);
        if (optionalPatient.isPresent()) {
            Patient p = optionalPatient.get();
            result += "Name : " + p.getName() + " | " + "Gender: " + p.getGender() + " | " + "Date of Birth: "
                    + p.getDob() + "<br>";
        }
        return result;
    }

    // Create a new patient
    @PostMapping("/add")
    public ResponseEntity<?> createPatient(@RequestBody Patient p) {
        if (p.getName() == null || p.getGender() == null || p.getDob() == null) {
            return new ResponseEntity<>("Missing fields", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(patRep.save(p), HttpStatus.CREATED);
    }

    // Update a patient information
    @PostMapping("/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient pDetails) {

        Patient p = patRep.findById(id).orElse(null);

        if (p != null) {
            p.setName(pDetails.getName());
            p.setGender(pDetails.getGender());
            p.setDob(pDetails.getDob());

            patRep.save(p);
        }
        return p;
    }

    // Delete a patient and all his/her appointments using cascading
    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable Long id) {
        patRep.deleteById(id);
    }
}
