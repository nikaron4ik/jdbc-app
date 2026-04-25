package dao;

import model.Enrollment;

import java.util.List;

public interface EnrollmentDao {
    void save(Enrollment enrollment);

    List<Enrollment> findAll();

    Enrollment findById(Long id);

    void update(Enrollment enrollment);

    void delete(Long id);

    List<String> findAllWithJoin();
}
