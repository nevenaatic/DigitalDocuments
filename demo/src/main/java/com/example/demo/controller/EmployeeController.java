package com.example.demo.controller;


import com.example.demo.dto.LogInDto;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
     EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<?> signIn(@RequestBody LogInDto logInDto){
        Employee employee = employeeService.getByName(logInDto.getName());
        if(employee == null)
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(employee.getId(), HttpStatus.OK);
    }
}
