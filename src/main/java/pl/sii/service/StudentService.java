package pl.sii.service;

import org.springframework.stereotype.Service;
import pl.sii.model.Student;

import java.util.*;

@Service
public class StudentService {

    private static final Map<Integer, Student> DB = new HashMap<>();

    public void deleteStudents() {
        DB.clear();
    }

    public Collection<Student> getStudents() {
        return DB.values();
    }

    public Optional<Student> getStudentById(Integer id) {
        return Optional.ofNullable(DB.get(id));
    }

    public Student addStudent(Student student) {
        Integer id = Optional.ofNullable(student.id()).orElseGet(DB::size);
        return DB.put(id, new Student(id, student.name()));
    }
}
