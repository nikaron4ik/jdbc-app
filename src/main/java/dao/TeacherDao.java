package dao;

import model.Teacher;

import java.util.List;

public interface TeacherDao {
    void save(Teacher teacher);

    List<Teacher> findAll();

    Teacher findById(Long id);

    void update(Teacher teacher);

    void delete(Long id);

    List<String> findAllWithJoin();
}
