package com.example.hospital.Controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.Entity.Appointment;
import com.example.hospital.Entity.Doctor;
import com.example.hospital.Repository.AppointmentRepository;
import com.example.hospital.Repository.DoctorRepository;

@RestController
@RequestMapping("doctor")
public class DoctorController {

    @Autowired
    DoctorRepository docRep;

    @Autowired
    AppointmentRepository appRep;

    @GetMapping
    public String getAllNames() {
        String result = "";

        for (Doctor d : docRep.findAll()) {
            result += "Name : " + d.getName() + " | " + "Specialty: " + d.getSpecialty() + "<br>";
        }

        return result;
    }

    // Get a single doctor by id with appointments and patients information
    @GetMapping("/{id}")
    public String getDoctorById(@PathVariable Long id) {
        String result = "";

        Optional<Doctor> optionalDoctor = docRep.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor d = optionalDoctor.get();
            result += "Name : " + d.getName() + " | " + "Specialty: " + d.getSpecialty() + " | " + d.getAppointments();

            for (Appointment a : d.getAppointments()) {
                result += "| ID: " + a.getId() + " " + " ; Patient: " + a.getPatient().getName() + " ";
            }
            result += "<br>";
        }
        return result;
    }

    // Create a new doctor
    @PostMapping("/add")
    public Doctor createDoctor(@RequestBody Doctor d) {
        return docRep.save(d);
    }

    // Update a doctor information
    @PostMapping("/update/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor d1) {

        Doctor d = docRep.findById(id).orElse(null);

        if (d != null) {
            d.setName(d1.getName());
            d.setSpeciality(d1.getSpecialty());

            docRep.save(d);
        }
        return d;
    }

    // Delete a doctor
    @PostMapping("/delete/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        docRep.deleteById(id);
    }

}
