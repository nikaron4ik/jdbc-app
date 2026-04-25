package dao;

import model.Course;

import java.util.List;

public interface CourseDao {
    void save(Course course);

    List<Course> findAll();

    Course findById(Long id);

    void update(Course course);

    void delete(Long id);

    List<String> findAllWithJoin();
}
