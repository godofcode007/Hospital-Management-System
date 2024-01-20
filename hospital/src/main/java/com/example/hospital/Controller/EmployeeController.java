package com.example.hospital.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.Entity.Employee;
import com.example.hospital.Repository.EmployeeRepository;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository empRep;

    // Get all employees
    @GetMapping
    public String getall() {
        String result = "";

        for (Employee e : empRep.findAll()) {
            result += "Name : " + e.getName() + " | " + "Position: " + e.getPosition() + " | " + "Hire Date: "
                    + e.getHireDate() + "<br>";
        }

        return result;
    }

    // Get a single employee by id
    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id) {
        Employee e = empRep.findById(id).orElse(null);
        return "Name : " + e.getName() + " | " + "Position: " + e.getPosition() + " | " + "Hire Date: "
                + e.getHireDate() + "<br>";
    }

    // Add an employee using a POST request in JSON format in Reqbin
    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return empRep.save(employee);
    }

    // Update an employee using a POST request in JSON format in Reqbin
    @PostMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee emp = empRep.findById(id).orElse(null);
        emp.setName(employee.getName());
        emp.setPosition(employee.getPosition());
        emp.setHireDate(employee.getHireDate());
        return empRep.save(emp);
    }

    // Delete an employee by id
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        empRep.deleteById(id);
        return "Employee Deleted";
    }

}
