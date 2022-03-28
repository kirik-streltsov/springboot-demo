package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService service;

    public StudentController(@Autowired StudentService service){
        this.service = service;
    }

    @GetMapping
    public List<Student> getStudents(){
        return service.getStudents();
    }

    @PostMapping
    public void addStudent(@RequestBody Student s){
        service.addStudent(s);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id){
        service.deleteStudent(id);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long id,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email){
        service.updateStudent(id, name, email);
    }
}
