package net.idrok.idrok.Controller;

import net.idrok.idrok.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StundentsController {

    @RequestMapping("/student")
    public ResponseEntity getAll(){
        Student student = new Student(1L,"sardor","Haydarov","onaltili");
        Student student1 = new Student(1L,"Abror","Haydarov","Bekorchilk");

        Student student2 = new Student(1L,"Alibek","Ganiyev","girtbekorchi");
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);
        students.add(student2);
        return ResponseEntity.ok(students);
    }
    @RequestMapping("/students/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        Student student = new Student(id,"sardor","Haydarov","onaltili");
        return ResponseEntity.ok(student);

    }
    @RequestMapping("/students")
    public ResponseEntity create(@RequestBody Student student) {
        return ResponseEntity.ok(student);
    }

}
