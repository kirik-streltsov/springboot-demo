package com.example.demo;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    public void tearDown(){
        underTest.deleteAll();
    }

    @Test
    public void checkIfStudentExistsByEmail(){

        String email = "email@example.com";

        Student s = new Student("Kirill",
                "Streltsov",
                email,
                LocalDate.of(2005, 1, 26)
        );

        underTest.save(s);

        boolean exists = underTest.findStudentByEmail(email).isPresent();

        assertThat(exists).isTrue();
    }

    @Test
    public void checkIfFalseForNonExistingEmail(){

        String email = "email@example.com";

        boolean exists = underTest.findStudentByEmail(email).isPresent();

        assertThat(exists).isFalse();
    }
}