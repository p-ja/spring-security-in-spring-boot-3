package pl.sii.rest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sii.model.Student;
import pl.sii.service.StudentService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
class StudentController {

    final StudentService service;

    StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/students")
    Collection<Student> getStudents() {
        return service.getStudents();
    }

    @DeleteMapping("/students")
    void deleteStudents() {
        service.deleteStudents();
    }

    @GetMapping("/students/{id}")
    ResponseEntity<Optional<Student>> getStudent(@PathVariable("id") Integer id) {
        return ResponseEntity.ofNullable(service.getStudentById(id));
    }

    @PutMapping("/students")
    Student putStudent(@RequestBody @Valid Student student) {
        return service.addStudent(student);
    }
}
