package com.knoldus.simplecrudapp.controller;

import com.knoldus.simplecrudapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    private EmployeeRepository employeeRepository;


}
