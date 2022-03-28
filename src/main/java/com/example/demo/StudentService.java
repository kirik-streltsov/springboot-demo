package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository){
        this.repository = repository;
    }

    public List<Student> getStudents(){
        return repository.findAll();
    }

    public void addStudent(Student s){
        if(repository.findStudentByEmail(s.getEmail()).isPresent()){
            throw new IllegalStateException(String.format("Student with id %s already exists.",
                    s.getEmail()));
        }
        repository.save(s);
    }

    public void deleteStudent(Long id){
        if(!repository.existsById(id)){
            throw new IllegalStateException(String.format("Student with id %d does not exist", id));
        }
        repository.deleteById(id);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email){

        System.out.printf("name=" + name + " email=" + email);

        Student s = repository.findById(id).orElseThrow(() -> new IllegalStateException(String
                .format("Student with id %d does not exist", id)));

        if(name != null && !s.getFirstName().equals(name) && name.length() > 0){
            s.setFirstName(name);
        }

        if(email != null && !email.equals(s.getEmail()) && email.length() > 0){
            Optional<Student> studentOptional = repository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException(String.format("Email %s is already taken", email));
            }
            s.setEmail(email);
        }
    }
}
