package net.idrok.idrok.Service;

import net.idrok.idrok.entity.Employee;
import net.idrok.idrok.repository.EmployeeRepsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepsitory employeeRepsitory;

    public EmployeeService(EmployeeRepsitory employeeRepsitory) {
        this.employeeRepsitory = employeeRepsitory;
    }

    public Employee save(Employee employee){
        return employeeRepsitory.save(employee);

    }
    public List<Employee> findAll(){
        return employeeRepsitory.findAll();
    }

    public  Employee findById(Long id){
        return employeeRepsitory.findById(id).get();

    }
  public List<Employee> findAllIsm(String ism){
        return employeeRepsitory.findAllByIsm(ism);
  }



}
