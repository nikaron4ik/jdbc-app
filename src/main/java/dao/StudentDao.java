package dao;

import model.Student;

import java.util.List;

public interface StudentDao {
    void save(Student student);

    List<Student> findAll();

    Student findById(Long id);

    void update(Student student);

    void delete(Long id);

    List<String> findAllWithJoin();
}
