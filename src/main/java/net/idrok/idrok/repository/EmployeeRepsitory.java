package net.idrok.idrok.repository;

import net.idrok.idrok.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepsitory extends JpaRepository<Employee,Long> {



//    Employee findByLastNameQuery(@Param("ism") String  lastname);

//    @Query(value = "select e from Employee e WHERE e.ism = :name " ,nativeQuery = true)
//   List<Employee>  findByLastNameQueryNative(@Param("ism") String  lastname);

    List<Employee> findAllByIsm(String ism);



}
