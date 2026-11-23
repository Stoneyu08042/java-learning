package org.example.studentapi.student;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500"
})
public class StudentController {
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello Spring Boot");
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return List.of(
                new Student(
                        1,
                        "张三",
                        "一班",
                        88.5,
                        "2026-10-01 09:00"
                ),
                new Student(
                        2,
                        "李四",
                        "二班",
                        72,
                        "2026-10-02 10:15"
                ),
                new Student(
                        3,
                        "石头",
                        "一班",
                        95,
                        "2026-10-03 14:30"
                ),
                new Student(
                        4,
                        "赵六",
                        "三班",
                        61.5,
                        "2026-10-04 08:45"
                ),
                new Student(
                        5,
                        "孙七",
                        "二班",
                        79,
                        "2026-10-05 16:20"
                )
        );
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable long id
    ) {
        Student student = findStudentById(id);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    private Student findStudentById(long id) {
        for (Student student : getStudents()) {
            // TODO:
            // 如果 student.getId() 等于 id，就返回这个 student。
            if (student.getId()==id){
                return student;
            }
        }

        return null;
    }
}
