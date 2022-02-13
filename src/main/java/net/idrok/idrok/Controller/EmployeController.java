package net.idrok.idrok.Controller;

import net.idrok.idrok.Service.EmployeeService;
import net.idrok.idrok.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employess")
    public List<Employee> getAll (){
        return employeeService.findAll();


    }


    @PostMapping("/employess")
    public Employee create(@RequestBody Employee employee){
        return employeeService.save(employee);

    }

    @PostMapping("/employess/{all}")
    public List<Employee> create(@PathVariable String ism ){
        return employeeService.findAllIsm(ism);
    }



}
